package com.example.demorestaurantapp.di

import com.example.demorestaurantapp.ui.main.viewmodel.RestaurantsViewModel
import dagger.Component
import javax.inject.Singleton

// main component where we define what classes where in which classes we want to access our module

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {

    fun inject(restaurantsViewModel: RestaurantsViewModel)

}