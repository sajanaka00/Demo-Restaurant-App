package com.example.demorestaurantapp.ui.main.view

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demorestaurantapp.R
import com.example.demorestaurantapp.data.api.YelpService
import com.example.demorestaurantapp.data.repository.ResImagesRepository
import com.example.demorestaurantapp.databinding.ActivityDisplayMenuImagesBinding
import com.example.demorestaurantapp.ui.base.ResImagesViewModelFactory
import com.example.demorestaurantapp.ui.main.adapter.ResImagesAdapter
import com.example.demorestaurantapp.ui.main.viewmodel.ResImagesViewModel

class DisplayMenuImages : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayMenuImagesBinding
    private lateinit var adapter : ResImagesAdapter
    private val retrofitService = YelpService.getInstance()
    private lateinit var viewModel: ResImagesViewModel
//    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_menu_images)

//        id = intent.getSerializableExtra(RESTAURANT_ID) as String
//        println(id)

        binding = ActivityDisplayMenuImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ResImagesViewModelFactory(ResImagesRepository(retrofitService))
        ).get(ResImagesViewModel::class.java)

        binding.displayMenuRV.layoutManager = LinearLayoutManager(this)

        adapter = ResImagesAdapter()
        binding.displayMenuRV.adapter = adapter

        viewModel.images.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            viewModel.images.value?.let { adapter.setRestaurantList(it) }
        })

        viewModel.errorMessage.observe(this, Observer {
        })

        viewModel.getRestaurantImages()
    }

//    fun getResId(): String {
//        return id
//    }
}