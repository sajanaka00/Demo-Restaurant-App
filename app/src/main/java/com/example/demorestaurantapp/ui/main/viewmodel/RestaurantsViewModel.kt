package com.example.demorestaurantapp.ui.main.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demorestaurantapp.data.model.YelpRestaurants
import com.example.demorestaurantapp.data.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
    * ViewModel class having the business logic and API call implementations
    * In the ViewModel constructor, we need to pass the data repository to handle the data
*/

@HiltViewModel
class RestaurantsViewModel @Inject constructor(private val repository: RestaurantsRepository) : ViewModel() {

    var restaurants: MutableLiveData<List<YelpRestaurants>>

    val costEffectiveList = ArrayList<YelpRestaurants>()
    val bitPricerList = ArrayList<YelpRestaurants>()
    val bigSpenderList = ArrayList<YelpRestaurants>()

    init {
        restaurants = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<YelpRestaurants>>{
        return restaurants
    }

    // function to make the api call
    fun loadListOfData() {
        repository.makeApiCall(restaurants)
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
                    repository.searchTerm = query
                    repository.makeApiCall(restaurants)
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