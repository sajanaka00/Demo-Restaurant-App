package com.example.demorestaurantapp.di

import com.example.demorestaurantapp.data.api.YelpService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// here we'll be implementing some functions which we are going to inject in our viewmodel

@Module
class RetroModule {

    val baseURL = "https://api.yelp.com/v3/"

    // returns the instance of retro service interface
    @Singleton
    @Provides
    fun getRetroServiceInterface(retrofit: Retrofit) : YelpService {
        return retrofit.create(YelpService::class.java)
    }

    // @Provides - we can inject this function wherever we want
    @Singleton
    @Provides
    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}