package com.example.demorestaurantapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
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
    private lateinit var retrofit : Retrofit
    private lateinit var yelpService : YelpService

    private lateinit var costEffectiveAdapter : RestaurantsAdapter
    private lateinit var bitPricerAdapter : RestaurantsAdapter
    private lateinit var bigSpenderAdapter : RestaurantsAdapter

    private val costEffectiveList = ArrayList<YelpRestaurants>()
    private val bitPricerList = ArrayList<YelpRestaurants>()
    private val bigSpenderList = ArrayList<YelpRestaurants>()

    private var searchTerm = "Pizza"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCostEffective = findViewById(R.id.costEffectiveRV)
        rvBitPricer = findViewById(R.id.bitPricerRV)
        rvBigSpender = findViewById(R.id.bigSpenderRV)
        searchBar = findViewById(R.id.search_bar)

        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        yelpService = retrofit.create(YelpService::class.java)

        getRestaurants()
        searchRestaurants()

        val costEffLayoutManager = LinearLayoutManager(applicationContext)
        costEffLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvCostEffective.layoutManager = costEffLayoutManager

        costEffectiveAdapter = RestaurantsAdapter(this, costEffectiveList, object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, costEffectiveList[position].id)
                startActivity(intent)
            }
        })
        rvCostEffective.adapter = costEffectiveAdapter

        val bitPricerLayoutManager = LinearLayoutManager(applicationContext)
        bitPricerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvBitPricer.layoutManager = bitPricerLayoutManager

        bitPricerAdapter = RestaurantsAdapter(this, bitPricerList, object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, bitPricerList[position].id)
                startActivity(intent)
            }
        })
        rvBitPricer.adapter = bitPricerAdapter

        val bigSpenderLayoutManager = LinearLayoutManager(applicationContext)
        bigSpenderLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvBigSpender.layoutManager = bigSpenderLayoutManager

        bigSpenderAdapter = RestaurantsAdapter(this, bigSpenderList, object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, bigSpenderList[position].id)
                startActivity(intent)
            }
        })
        rvBigSpender.adapter = bigSpenderAdapter
    }

    private fun getRestaurants() {
        yelpService.searchRestaurants("Bearer $API_KEY", searchTerm, "New York")
            .enqueue(object : Callback<YelpSearchResult> {
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Did not receive valid response body from Yelp API")
                        return
                    }
                    restaurants.clear()
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
        costEffectiveList.clear()
        bitPricerList.clear()
        bigSpenderList.clear()

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

//        println("costEffectiveList: $costEffectiveList")
//        println("bitPricerList: $bitPricerList")
//        println("bigSpenderList: $bigSpenderList")

    }

    private fun searchRestaurants() {
        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println("Search Query: $query")
                if (query != null) {
                    searchTerm = query
                    getRestaurants()
                } else {
                    Log.i(TAG, "Invalid Input")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

}