package com.example.demorestaurantapp

import android.app.Application
import com.example.demorestaurantapp.di.DaggerRetroComponent
import com.example.demorestaurantapp.di.RetroComponent
import com.example.demorestaurantapp.di.RetroModule

class MyApplication: Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()

        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()
    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }
}