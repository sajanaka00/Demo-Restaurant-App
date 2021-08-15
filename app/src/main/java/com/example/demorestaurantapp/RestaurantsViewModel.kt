package com.example.demorestaurantapp

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantsViewModel constructor(private val repository: RestaurantsRepository) : ViewModel() {

    val restaurants = MutableLiveData<List<YelpRestaurants>>()

    val costEffectiveList = ArrayList<YelpRestaurants>()
    val bitPricerList = ArrayList<YelpRestaurants>()
    val bigSpenderList = ArrayList<YelpRestaurants>()

    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {

        val response = repository.getAllMovies()
        response.enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(
                call: Call<YelpSearchResult>,
                response: Response<YelpSearchResult>
            ) {
                Log.i(ContentValues.TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(ContentValues.TAG, "Did not receive valid response body from Yelp API")
                    return
                }
                restaurants.postValue(body.restaurants)
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(ContentValues.TAG, "onFailure $t")
                errorMessage.postValue(t.message)
            }
        })

    }

    fun categorizeItems(restaurants: List<YelpRestaurants>) {

        for (restaurant in restaurants) {
            val restaurantPrice: String = restaurant.price
            if (restaurantPrice == "$") {
                costEffectiveList.add(restaurant)
            }
            if (restaurantPrice == "$$") {
                bitPricerList.add(restaurant)
            }
            if (restaurantPrice == "$$$") {
                bigSpenderList.add(restaurant)
            }
        }

        println("costEffectiveList: $costEffectiveList")
        println("bitPricerList: $bitPricerList")
        println("bigSpenderList: $bigSpenderList")
    }

}