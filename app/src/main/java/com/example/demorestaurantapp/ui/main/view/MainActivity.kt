package com.example.demorestaurantapp.ui.main.view

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demorestaurantapp.R
import com.example.demorestaurantapp.data.api.YelpService
import com.example.demorestaurantapp.data.model.ParentModel
import com.example.demorestaurantapp.ui.main.adapter.ParentAdapter
import com.example.demorestaurantapp.ui.main.viewmodel.RestaurantsViewModel

const val RESTAURANT_ID = "RESTAURANT_ID"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var viewModel: RestaurantsViewModel

    private val retrofitService = YelpService.getInstance()
    private val items: ArrayList<ParentModel> = ArrayList()
    private lateinit var searchBar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBar = findViewById(R.id.search_bar)

        initViewModel()

//        viewModel = ViewModelProvider(
//            this,
//            RestaurantsViewModelFactory(RestaurantsRepository(retrofitService))
//        ).get(RestaurantsViewModel::class.java)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RestaurantsViewModel::class.java)

        viewModel.getRestaurants()
//        viewModel.searchRestaurants(searchBar)

        // The observer will only receive events if the owner is in STARTED or RESUMED state
        /* Params:
            owner – The LifecycleOwner which controls the observer
            observer – The observer that will receive the events
         */
        viewModel.restaurants.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            viewModel.categorizeItems(it)

            getItems().let { items.addAll(it) }

            val adapter = ParentAdapter(this, items)

            val recyclerView: RecyclerView = findViewById(R.id.parentRecyclerView)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        })

        viewModel.errorMessage.observe(this, Observer {

        })
    }

    private fun getItems(): ArrayList<ParentModel> {
        val items = ArrayList<ParentModel>()

        val item1 = ParentModel()
        item1.title = "Cost Effective"
        item1.items.addAll(viewModel.costEffectiveList)

        items.add(item1)

        val item2 = ParentModel()
        item2.title = "Bit Pricer"
        item2.items.addAll(viewModel.bitPricerList)

        items.add(item2)

        val item3 = ParentModel()
        item3.title = "Big Spender"
        item3.items.addAll(viewModel.bigSpenderList)

        items.add(item3)

        return items
    }

}