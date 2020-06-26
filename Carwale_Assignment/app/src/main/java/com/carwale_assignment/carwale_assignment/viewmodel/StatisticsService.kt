package com.carwale_assignment.carwale_assignment.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.carwale_assignment.carwale_assignment.model.StatisticsResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StatisticsService {
    val liveUserResponse: MutableLiveData<StatisticsResponse> = MutableLiveData()
    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): StatisticsInterface {
            Log.d("retrofit","create")
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.covid19api.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            return retrofit.create(StatisticsInterface::class.java)
        }
    }

    fun getCountryList(): MutableLiveData<StatisticsResponse>? {
        val retrofitCall  = create().getCountryList()
        retrofitCall.enqueue(object : Callback<StatisticsResponse> {
            override fun onFailure(call: Call<StatisticsResponse>, t: Throwable?) {
                Log.e("on Failure :", "retrofit error")
            }
            override fun onResponse(call: Call<StatisticsResponse>, response: retrofit2.Response<StatisticsResponse>) {
                val list  = response.body()

                liveUserResponse?.value = list

            }
        })
        return liveUserResponse
    }
}