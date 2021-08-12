package com.example.demorestaurantapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yelp.fusion.client.connection.YelpFusionApiFactory
import com.yelp.fusion.client.models.Business
import com.yelp.fusion.client.models.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "YelpApi"
private const val API_KEY = "ImI1man5FXDZTZz2g7mmZ3_ChOU55GqU7OFfFaBj6ObY_E5s9_OAmz2dpQcUdlGV1dwqnE-GMLudXpUxjUxNl--BCb0SJaXPYTbSnI8l9mibhGf2raVwcfkUCOb_YHYx"

class YelpApi {

    val api = YelpFusionApiFactory().createAPI(API_KEY)

    fun search(): LiveData<List<Business>> {

        val data = MutableLiveData<List<Business>>()

        api.getBusinessSearch(getParams()).enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    response.body().let {
                        Log.i("Search response %s", response.body().toString())
                        data.value = it?.businesses
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            }
        )
        return data
    }

    private fun getParams(): Map<String, String> {

        val paramMap = HashMap<String, String>()

        paramMap["location"] = "San Francisco"
        paramMap["term"] = "Coffee"

        return paramMap
    }

}