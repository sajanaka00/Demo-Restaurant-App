package com.example.demorestaurantapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RestaurantsViewModelFactory constructor(private val repository: RestaurantsRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(RestaurantsViewModel::class.java)) {
            RestaurantsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }
}