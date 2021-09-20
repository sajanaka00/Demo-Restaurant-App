package com.example.demorestaurantapp.ui.main.view

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demorestaurantapp.R
import com.example.demorestaurantapp.data.model.ParentModel
import com.example.demorestaurantapp.ui.main.adapter.ParentAdapter
import com.example.demorestaurantapp.ui.main.viewmodel.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint

const val RESTAURANT_ID = "RESTAURANT_ID"

/* @AndroidEntryPoint - Marks an Android component class to be setup for injection with the
    standard Hilt Dagger Android components.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var viewModel: RestaurantsViewModel
    private val items: ArrayList<ParentModel> = ArrayList()
    private lateinit var searchBar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBar = findViewById(R.id.search_bar)

        initViewModel()
        viewModel.searchRestaurants(searchBar)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RestaurantsViewModel::class.java)

        // The observer will only receive events if the owner is in STARTED or RESUMED state
        /* Params:
            owner – The LifecycleOwner which controls the observer
            observer – The observer that will receive the events
         */
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                Log.d(TAG, "onCreate: $it")
                viewModel.categorizeItems(it)

                getItems().let { items.addAll(it) }

                val adapter = ParentAdapter(this, items)

                val recyclerView: RecyclerView = findViewById(R.id.parentRecyclerView)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this, "error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.loadListOfData()
    }

    private fun getItems(): ArrayList<ParentModel> {
        val items = ArrayList<ParentModel>()
        items.clear()

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