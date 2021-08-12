package com.example.demorestaurantapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demorestaurantapp.databinding.ActivityMainBinding
import com.yelp.fusion.client.models.Business

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RestaurantsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {

        val rvAdapter = RestaurantsViewAdapter()

        val costEffLayoutManager = LinearLayoutManager(applicationContext)
        costEffLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.costEffectiveRV.layoutManager = costEffLayoutManager
        binding.costEffectiveRV.adapter = rvAdapter

        viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(RestaurantsViewModel::class.java)

        viewModel.data.observe(this, {
            rvAdapter.data = it as ArrayList<Business>
            rvAdapter.notifyDataSetChanged()
        })
    }

//    private fun getRestaurants() {
//        yelpService.searchRestaurants("Bearer $API_KEY", searchTerm, "New York")
//            .enqueue(object : Callback<YelpSearchResult> {
//                override fun onResponse(
//                    call: Call<YelpSearchResult>,
//                    response: Response<YelpSearchResult>
//                ) {
//                    Log.i(TAG, "onResponse $response")
//                    val body = response.body()
//                    if (body == null) {
//                        Log.w(TAG, "Did not receive valid response body from Yelp API")
//                        return
//                    }
//                    restaurants.clear()
//                    restaurants.addAll(body.restaurants)
////                println(restaurants)
//                    categorizeItems(restaurants)
//                    costEffectiveAdapter.notifyDataSetChanged()
//                    bitPricerAdapter.notifyDataSetChanged()
//                    bigSpenderAdapter.notifyDataSetChanged()
//                }
//
//                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
//                    Log.i(TAG, "onFailure $t")
//                }
//            })
//    }
//
//    private fun categorizeItems(restaurants: MutableList<YelpRestaurants>) {
//        costEffectiveList.clear()
//        bitPricerList.clear()
//        bigSpenderList.clear()
//
//        for (restaurant in restaurants) {
//            val restaurantPrice: String = restaurant.price
//            if (restaurantPrice == "$") {
//                costEffectiveList.add(restaurant)
//            }
//            if (restaurantPrice == "$$") {
//                bitPricerList.add(restaurant)
//            }
//            if (restaurantPrice == "$$$") {
//                bigSpenderList.add(restaurant)
//            }
//        }
//
////        println("costEffectiveList: $costEffectiveList")
////        println("bitPricerList: $bitPricerList")
////        println("bigSpenderList: $bigSpenderList")
//
//    }
//
//    private fun searchRestaurants() {
//        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                println("Search Query: $query")
//                if (query != null) {
//                    searchTerm = query
//                    getRestaurants()
//                } else {
//                    Log.i(TAG, "Invalid Input")
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//    }

}