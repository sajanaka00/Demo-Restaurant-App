package com.example.demorestaurantapp.ui.main.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demorestaurantapp.data.model.YelpBusinessDetail
import com.example.demorestaurantapp.data.model.YelpRestaurants
import com.example.demorestaurantapp.data.repository.ResImagesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResImagesViewModel constructor(private val repository: ResImagesRepository)
    : ViewModel() {

    val images = MutableLiveData<List<String>>()
    val errorMessage = MutableLiveData<String>()

    fun getRestaurantImages() {

        val response = repository.getRestaurantImages()
        response.enqueue(object : Callback<YelpBusinessDetail> {
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
                images.postValue(body.photos)
            }

            override fun onFailure(call: Call<YelpBusinessDetail>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
                errorMessage.postValue(t.message)
            }
        })

    }

}