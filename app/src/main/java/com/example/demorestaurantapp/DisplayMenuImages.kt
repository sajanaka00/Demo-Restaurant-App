package com.example.demorestaurantapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "DisplayMenuImages"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"

class DisplayMenuImages : AppCompatActivity() {

    private lateinit var rvDisplayMenu: RecyclerView

    private lateinit var id: String
    val photos = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_menu_images)

        id = intent.getSerializableExtra(RESTAURANT_ID) as String

        rvDisplayMenu = findViewById(R.id.displayMenuRV)

        rvDisplayMenu.layoutManager = LinearLayoutManager(this)
        val displayMenuImagesAdapter = ImagesAdapter(this, photos)
        rvDisplayMenu.adapter = displayMenuImagesAdapter

        println(id)

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val yelpService = retrofit.create(YelpFusionService::class.java)
        yelpService.getDetails("Bearer $API_KEY", id)
            .enqueue(object : Callback<YelpBusinessDetail> {
                override fun onResponse(
                    call: Call<YelpBusinessDetail>,
                    response: Response<YelpBusinessDetail>
                ) {
                    Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Did not receive valid response body from Yelp API...")
                        return
                    }
                    photos.addAll(body.photos.drop(1))
                    displayMenuImagesAdapter.notifyDataSetChanged()
                    println(photos)
                }

                override fun onFailure(call: Call<YelpBusinessDetail>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })

    }
}