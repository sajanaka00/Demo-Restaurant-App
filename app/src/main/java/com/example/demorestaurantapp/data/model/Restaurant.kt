package com.example.demorestaurantapp.data.model

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurants>
)

data class YelpRestaurants(
    val id: String,
    val name: String,
    val rating: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("image_url") val imageUrl: String,
    val price: String
)

data class YelpBusinessDetail(
    val photos: List<String>,
)