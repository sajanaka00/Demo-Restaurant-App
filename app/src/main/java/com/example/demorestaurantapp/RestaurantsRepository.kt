package com.example.demorestaurantapp

private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_" +
        "OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"

/*
    * I am using a repository pattern to handle the data from API
    * In the repository class, we need to pass the retrofit service instance to perform the network call
*/

class RestaurantsRepository constructor(private val yelpService: YelpService) {

    fun getAllMovies() = yelpService.searchRestaurants(
        "Bearer $API_KEY",
        "Pizza",
        "New York"
    )

}
