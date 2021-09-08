package com.example.demorestaurantapp.ui.main.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.demorestaurantapp.MyApplication
import com.example.demorestaurantapp.data.api.YelpService
import com.example.demorestaurantapp.data.model.YelpRestaurants
import com.example.demorestaurantapp.data.model.YelpSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/*
    * ViewModel class having the business logic and API call implementations
    * In the ViewModel constructor, we need to pass the data repository to handle the data
*/

private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_" +
        "OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"

class RestaurantsViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mService: YelpService

    val restaurants = MutableLiveData<List<YelpRestaurants>>()

    val costEffectiveList = ArrayList<YelpRestaurants>()
    val bitPricerList = ArrayList<YelpRestaurants>()
    val bigSpenderList = ArrayList<YelpRestaurants>()

    val errorMessage = MutableLiveData<String>()

    var searchTerm = "Pizza"

    init {
        (application as MyApplication).getRetroComponent().inject(this)
    }

    fun getRestaurants() {

        val call: Call<YelpSearchResult> =
            mService.searchRestaurants("Bearer $API_KEY", searchTerm, "New York")

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

    fun categorizeItems(restaurants: List<YelpRestaurants>) {
        costEffectiveList.clear()
        bitPricerList.clear()
        bigSpenderList.clear()

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

    fun searchRestaurants(searchBar: SearchView) {
        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println("Search Query: $query")
                if (query != null) {
                    searchTerm = query
                    getRestaurants()
                } else {
                    Log.i(TAG, "Invalid Input")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

}