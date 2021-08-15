package com.example.demorestaurantapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demorestaurantapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: RestaurantsViewModel

    private val retrofitService = YelpService.getInstance()
    val costEffectiveAdapter = RestaurantsAdapter()
    val bitPricerAdapter = RestaurantsAdapter()
    val bigSpenderAdapter = RestaurantsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, RestaurantsViewModelFactory(RestaurantsRepository(retrofitService))).get(RestaurantsViewModel::class.java)

        val costEffLayoutManager = LinearLayoutManager(applicationContext)
        costEffLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.costEffectiveRV.layoutManager = costEffLayoutManager
        binding.costEffectiveRV.adapter = costEffectiveAdapter

        val bitPricerLayoutManager = LinearLayoutManager(applicationContext)
        bitPricerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bitPricerRV.layoutManager = bitPricerLayoutManager
        binding.bitPricerRV.adapter = bitPricerAdapter

        val bigSpenderLayoutManager = LinearLayoutManager(applicationContext)
        bigSpenderLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bigSpenderRV.layoutManager = bigSpenderLayoutManager
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

        viewModel.getAllMovies()
    }

}