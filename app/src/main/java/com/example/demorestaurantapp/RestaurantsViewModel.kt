package com.example.demorestaurantapp

import androidx.lifecycle.ViewModel

class RestaurantsViewModel : ViewModel() {

    private val repo = RestaurantsRepository()
    val data = repo.search()

}