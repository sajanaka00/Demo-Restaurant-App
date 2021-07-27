package com.example.demorestaurantapp

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurants>
)

data class YelpRestaurants(
    val name: String,
    val rating: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>
)

data class YelpCategory(
    val title: String
)