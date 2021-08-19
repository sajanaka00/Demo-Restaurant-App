package com.example.demorestaurantapp.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demorestaurantapp.R
import com.example.demorestaurantapp.ui.main.viewmodel.RestaurantsViewModel
import com.example.demorestaurantapp.data.api.YelpService
import com.example.demorestaurantapp.data.repository.RestaurantsRepository
import com.example.demorestaurantapp.databinding.ActivityMainBinding
import com.example.demorestaurantapp.ui.base.RestaurantsViewModelFactory
import com.example.demorestaurantapp.ui.main.adapter.RestaurantsAdapter

const val RESTAURANT_ID = "RESTAURANT_ID"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: RestaurantsViewModel

    private val retrofitService = YelpService.getInstance()

    private lateinit var costEffectiveAdapter : RestaurantsAdapter
    private lateinit var bitPricerAdapter : RestaurantsAdapter
    private lateinit var bigSpenderAdapter : RestaurantsAdapter

    private lateinit var searchBar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBar = findViewById(R.id.search_bar)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            RestaurantsViewModelFactory(RestaurantsRepository(retrofitService))
        ).get(RestaurantsViewModel::class.java)

        val costEffLayoutManager = LinearLayoutManager(applicationContext)
        costEffLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.costEffectiveRV.layoutManager = costEffLayoutManager

        costEffectiveAdapter = RestaurantsAdapter(object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, viewModel.costEffectiveList[position].id)
                startActivity(intent)
            }
        })
        binding.costEffectiveRV.adapter = costEffectiveAdapter

        val bitPricerLayoutManager = LinearLayoutManager(applicationContext)
        bitPricerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bitPricerRV.layoutManager = bitPricerLayoutManager

        bitPricerAdapter = RestaurantsAdapter(object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, viewModel.bitPricerList[position].id)
                startActivity(intent)
            }
        })
        binding.bitPricerRV.adapter = bitPricerAdapter

        val bigSpenderLayoutManager = LinearLayoutManager(applicationContext)
        bigSpenderLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bigSpenderRV.layoutManager = bigSpenderLayoutManager

        bigSpenderAdapter = RestaurantsAdapter(object : RestaurantsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DisplayMenuImages::class.java)
                intent.putExtra(RESTAURANT_ID, viewModel.bigSpenderList[position].id)
                startActivity(intent)
            }
        })
        binding.bigSpenderRV.adapter = bigSpenderAdapter

        // The observer will only receive events if the owner is in STARTED or RESUMED state
        /* Params:
            owner – The LifecycleOwner which controls the observer
            observer – The observer that will receive the events
         */
        viewModel.restaurants.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            viewModel.categorizeItems(it)

            costEffectiveAdapter.setRestaurantList(viewModel.costEffectiveList)
            bitPricerAdapter.setRestaurantList(viewModel.bitPricerList)
            bigSpenderAdapter.setRestaurantList(viewModel.bigSpenderList)
        })

        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.getRestaurants()
//        viewModel.searchRestaurants(searchBar)
    }

}