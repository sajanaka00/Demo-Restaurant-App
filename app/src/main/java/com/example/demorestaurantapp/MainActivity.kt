package com.example.demorestaurantapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"
const val RESTAURANT_ID = "RESTAURANT_ID"

class MainActivity : AppCompatActivity() {

    private lateinit var rvCostEffective: RecyclerView
    private lateinit var rvBitPricer: RecyclerView
    private lateinit var rvBigSpender: RecyclerView
    private lateinit var searchBar: SearchView

    val restaurants = mutableListOf<YelpRestaurants>()

    private val costEffectiveList = ArrayList<YelpRestaurants>()
    private val bitPricerList = ArrayList<YelpRestaurants>()
    private val bigSpenderList = ArrayList<YelpRestaurants>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCostEffective = findViewById(R.id.costEffectiveRV)
        rvBitPricer = findViewById(R.id.bitPricerRV)
        rvBigSpender = findViewById(R.id.bigSpenderRV)
        searchBar = findViewById(R.id.search_bar)

//        rvCostEffective.layoutManager = LinearLayoutManager(this)

        val costEffLayoutManager = LinearLayoutManager(applicationContext)
        costEffLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvCostEffective.layoutManager = costEffLayoutManager

        val costEffectiveAdapter = RestaurantsAdapter(this, costEffectiveList, object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                // When the user taps on a view in RV, navigate to new activity
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, restaurants[position].id)
                startActivity(intent)
            }
        })
        rvCostEffective.adapter = costEffectiveAdapter

        val bitPricerLayoutManager = LinearLayoutManager(applicationContext)
        bitPricerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvBitPricer.layoutManager = bitPricerLayoutManager

        val bitPricerAdapter = RestaurantsAdapter(this, bitPricerList, object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                // When the user taps on a view in RV, navigate to new activity
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, restaurants[position].id)
                startActivity(intent)            }
        })
        rvBitPricer.adapter = bitPricerAdapter

        val bigSpenderLayoutManager = LinearLayoutManager(applicationContext)
        bigSpenderLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvBigSpender.layoutManager = bigSpenderLayoutManager

        val bigSpenderAdapter = RestaurantsAdapter(this, bigSpenderList, object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                // When the user taps on a view in RV, navigate to new activity
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, restaurants[position].id)
                startActivity(intent)            }
        })
        rvBigSpender.adapter = bigSpenderAdapter

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val yelpService = retrofit.create(YelpFusionService::class.java)

        yelpService.searchRestaurants("Bearer $API_KEY", "Pizza", "New York")
            .enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API...")
                    return
                }
                restaurants.addAll(body.restaurants)
//                println(restaurants)
                categorizeItems(restaurants)
                costEffectiveAdapter.notifyDataSetChanged()
                bitPricerAdapter.notifyDataSetChanged()
                bigSpenderAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })

    }

    private fun categorizeItems(restaurants: MutableList<YelpRestaurants>) {
        for (restaurant in restaurants) {
            val restaurantPrice: String = restaurant.price
            if (restaurantPrice == "$") {
                costEffectiveList.add(restaurant)
            }
            if (restaurantPrice == "$$") {
                bitPricerList.add(restaurant)
            }
            if (restaurantPrice == "$$$") {
                bigSpenderList.add(restaurant)
            }
        }

        println(costEffectiveList)
        println(bitPricerList)
        println(bigSpenderList)

    }

}