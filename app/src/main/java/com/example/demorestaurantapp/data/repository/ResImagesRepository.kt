package com.example.demorestaurantapp.data.repository

import com.example.demorestaurantapp.data.api.YelpService
import com.example.demorestaurantapp.ui.main.view.DisplayMenuImages

private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_" +
        "OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"

class ResImagesRepository constructor(private val yelpService: YelpService) {

//    private var displayMenuImages: DisplayMenuImages = DisplayMenuImages()
//    var id = displayMenuImages.getResId()

    var id = "zj8Lq1T8KIC5zwFief15jg"

    fun getRestaurantImages() = yelpService.getDetails(
        "Bearer $API_KEY",
        id
    )

}