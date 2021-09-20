package com.example.demorestaurantapp.di

import com.example.demorestaurantapp.data.api.YelpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// this class will basically return the retrofit object

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val baseURL = "https://api.yelp.com/v3/"

    // returns the instance of retro service interface
    @Singleton
    @Provides
    fun getRetroServiceInterface(retrofit: Retrofit) : YelpService {
        return retrofit.create(YelpService::class.java)
    }

    // @Provides - we can inject this function wherever we want
    // this function returns the retrofit object
    @Singleton
    @Provides
    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}