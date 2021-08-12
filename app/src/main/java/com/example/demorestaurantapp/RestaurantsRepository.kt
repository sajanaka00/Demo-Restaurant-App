package com.example.demorestaurantapp

import androidx.lifecycle.MutableLiveData
import com.yelp.fusion.client.models.Business

class RestaurantsRepository {

    private val yelpService = YelpService()
    private val yelpApi = yelpService.createApi()

    var data: MutableLiveData<List<Business>> = MutableLiveData<List<Business>>()

    fun search(): MutableLiveData<List<Business>> {
        data = yelpApi.search() as MutableLiveData<List<Business>>
        return data
    }

}
