package com.example.demorestaurantapp

import android.app.Application
import com.example.demorestaurantapp.di.DaggerRetroComponent
import com.example.demorestaurantapp.di.RetroComponent
import com.example.demorestaurantapp.di.RetroModule

class MyApplication: Application() {

    /* application class - base class within an Android app that contains all other components such as
    activities and services
        is instantiated before any other class when the process for your application/package is created
     */

    private lateinit var retroComponent: RetroComponent

    // this is the first function the application calls when launching
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
