package com.example.demorestaurantapp.di

import com.example.demorestaurantapp.data.api.YelpService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetroModule {

    val baseURL = "https://api.yelp.com/v3/"

    @Singleton
    @Provides
    fun getRetroServiceInterface(retrofit: Retrofit) : YelpService {
        return retrofit.create(YelpService::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}