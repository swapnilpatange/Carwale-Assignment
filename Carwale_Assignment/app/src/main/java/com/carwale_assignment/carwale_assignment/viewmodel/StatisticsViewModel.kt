package com.carwale_assignment.carwale_assignment.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.carwale_assignment.carwale_assignment.model.Country
import com.carwale_assignment.carwale_assignment.model.FilterModel
import com.carwale_assignment.carwale_assignment.model.StatisticsResponse

class StatisticsViewModel : ViewModel() {
    private val mService = StatisticsService()
    private var filterModel: MutableLiveData<FilterModel>? = MutableLiveData()
    private var countries: ArrayList<Country>? = null
    fun getLiveCountryList(): MutableLiveData<StatisticsResponse>? {
        return mService.getCountryList()
    }

    fun getFilterModel(): MutableLiveData<FilterModel>? {
        return filterModel
    }

    fun setFiltermodel(mfilterModel: FilterModel) {
        filterModel?.value = mfilterModel
    }

    fun setCountryList(mcountries: ArrayList<Country>) {
        countries = mcountries
    }

    fun getCountryList(): ArrayList<Country> {
        return countries!!
    }


}