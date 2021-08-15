package com.example.demorestaurantapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpService {

    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("location") location: String
    ) : Call<YelpSearchResult>

    @GET("businesses/{id}")
    fun getDetails(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<YelpBusinessDetail>

    companion object {

        var retrofitService: YelpService? = null

        fun getInstance() : YelpService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.yelp.com/v3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(YelpService::class.java)
            }
            return retrofitService!!
        }

    }

}
