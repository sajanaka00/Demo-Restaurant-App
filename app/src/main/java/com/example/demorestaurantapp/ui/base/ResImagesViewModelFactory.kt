package com.example.demorestaurantapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demorestaurantapp.data.repository.ResImagesRepository
import com.example.demorestaurantapp.ui.main.viewmodel.ResImagesViewModel

class ResImagesViewModelFactory constructor(private val repository: ResImagesRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(ResImagesViewModel::class.java)) {
            ResImagesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }

}