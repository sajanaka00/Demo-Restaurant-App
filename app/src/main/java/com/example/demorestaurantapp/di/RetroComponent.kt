package com.example.demorestaurantapp.di

import com.example.demorestaurantapp.ui.main.viewmodel.RestaurantsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {

    fun inject(restaurantsViewModel: RestaurantsViewModel)

}