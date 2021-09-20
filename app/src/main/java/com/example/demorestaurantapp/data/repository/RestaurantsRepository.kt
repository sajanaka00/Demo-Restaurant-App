package com.example.demorestaurantapp.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.demorestaurantapp.data.api.YelpService
import com.example.demorestaurantapp.data.model.YelpRestaurants
import com.example.demorestaurantapp.data.model.YelpSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_" +
        "OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"

/*
    * I am using a repository pattern to handle the data from API
    * In the repository class, we need to pass the retrofit service instance to perform the network call
*/

// the constructor holds the instance of the yelp service interface
class RestaurantsRepository @Inject constructor(private val yelpService: YelpService) {

    var searchTerm = "Pizza"

    val errorMessage = MutableLiveData<String>()

    // using livedata as a parameter to send the call back to the main activity
    fun makeApiCall(restaurants: MutableLiveData<List<YelpRestaurants>>) {
        val call: Call<YelpSearchResult> =
            yelpService.searchRestaurants("Bearer $API_KEY", searchTerm, "New York")

        call.enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(
                call: Call<YelpSearchResult>,
                response: Response<YelpSearchResult>
            ) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API")
                    return
                }
                restaurants.postValue(body.restaurants)
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
                errorMessage.postValue(t.message)
            }
        })

    }

}
