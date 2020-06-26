package com.carwale_assignment.carwale_assignment.viewmodel

import com.carwale_assignment.carwale_assignment.model.StatisticsResponse
import retrofit2.Call
import retrofit2.http.GET

interface StatisticsInterface {
    @GET("summary")
    fun getCountryList(): Call<StatisticsResponse>
}