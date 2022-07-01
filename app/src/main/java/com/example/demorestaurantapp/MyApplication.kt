package com.example.demorestaurantapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/* Application class in Android is the base class within an Android app that contains all
other components such as activities and services
 */

@HiltAndroidApp
class MyApplication: Application() {
}