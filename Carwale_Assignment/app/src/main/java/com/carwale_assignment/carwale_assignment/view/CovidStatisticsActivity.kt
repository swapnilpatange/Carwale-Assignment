package com.carwale_assignment.carwale_assignment.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.carwale_assignment.carwale_assignment.utility.CommonUtilities
import com.carwale_assignment.carwale_assignment.R
import com.carwale_assignment.carwale_assignment.utility.AppConstants
import com.carwale_assignment.carwale_assignment.model.Country
import com.carwale_assignment.carwale_assignment.model.FilterModel
import com.carwale_assignment.carwale_assignment.model.StatisticsResponse
import com.carwale_assignment.carwale_assignment.utility.PrefManager
import com.carwale_assignment.carwale_assignment.viewmodel.StatisticsViewModel
import kotlinx.android.synthetic.main.activity_covid_statistics.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class CovidStatisticsActivity : AppCompatActivity(), View.OnClickListener {


    val commonUtilities = CommonUtilities()
    var statisticsViewModel: StatisticsViewModel? = null

    class ComparatorCountryAsc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o1.Country.compareTo(o2.Country)
        }
    }

    class ComparatorCountryDesc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {

            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1;
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1;

            return o2.Country.compareTo(o1.Country)
        }
    }


    class ComparatorCasesDesc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o2.TotalConfirmed - o1.TotalConfirmed
        }
    }

    class ComparatorCasesAsc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0;
            }

            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o1.TotalConfirmed - o2.TotalConfirmed
        }
    }


    class ComparatorDeathsAsc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o1.TotalDeaths - o2.TotalDeaths
        }
    }

    class ComparatorDeathsDesc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o2.TotalDeaths - o1.TotalDeaths
        }
    }


    class ComparatorRecoveredAsc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o1.TotalRecovered - o2.TotalRecovered
        }
    }

    class ComparatorRecoveredDesc(mactivity: Activity) : Comparator<Country> {
        val activity: Activity = mactivity
        override fun compare(o1: Country?, o2: Country?): Int {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return -1
            if (o2!!.Country.equals(PrefManager().getUserCountry(activity), true))
                return 1
            return o2.TotalRecovered - o1.TotalRecovered
        }
    }

    override fun onClick(view: View?) {
        //var  dataList=ArrayList<Country>()
        if (statisticsViewModel?.getCountryList() != null && statisticsViewModel?.getCountryList()!!.size > 0) {
            when (view?.id) {
                R.id.countryAscending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorCountryAsc(this), AppConstants.COUNTRY_ASCENDING)
                    countryAscending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.countryDescending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorCountryDesc(this), AppConstants.COUNTRY_DESCENDING)
                    countryDescending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.casesAscending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorCasesAsc(this), AppConstants.CASES_ASCENDING)
                    casesAscending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.casesDescending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorCasesDesc(this), AppConstants.CASES_DESCENDING)
                    casesDescending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.deathsAscending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorDeathsAsc(this), AppConstants.DEATHS_ASCENDING)
                    deathsAscending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.deathsDescending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorDeathsDesc(this), AppConstants.DEATHS_DESCENDING)
                    deathsDescending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.recoveredAscending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorRecoveredAsc(this), AppConstants.RECOVERED_ASCENDING)
                    recoveredAscending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.recoveredDescending -> {
                    removePreviousTint(statisticsViewModel?.getFilterModel()?.value!!.currentSort)
                    setSorting(ComparatorRecoveredDesc(this), AppConstants.RECOVERED_DESCENDING)
                    recoveredDescending.setColorFilter(resources.getColor(R.color.red))
                }
                R.id.filter -> {

                    val filterBottomSheetFragment = FilterBottomSheetFragment()
                    filterBottomSheetFragment.show(supportFragmentManager, "")

                }

            }
        }
    }

    fun refreshList(sortingText: String, lastSort: String) {
        removePreviousTint(lastSort)
        when (sortingText) {
            AppConstants.COUNTRY_ASCENDING -> {
                setSorting(ComparatorCountryAsc(this), AppConstants.COUNTRY_ASCENDING)
                countryAscending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.COUNTRY_DESCENDING -> {
                setSorting(ComparatorCountryDesc(this), AppConstants.COUNTRY_DESCENDING)
                countryDescending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.CASES_ASCENDING -> {
                setSorting(ComparatorCasesAsc(this), AppConstants.CASES_ASCENDING)
                casesAscending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.CASES_DESCENDING -> {
                setSorting(ComparatorCasesDesc(this), AppConstants.CASES_DESCENDING)
                casesDescending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.DEATHS_ASCENDING -> {
                setSorting(ComparatorDeathsAsc(this), AppConstants.DEATHS_ASCENDING)
                deathsAscending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.DEATHS_DESCENDING -> {
                setSorting(ComparatorDeathsDesc(this), AppConstants.DEATHS_DESCENDING)
                deathsDescending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.RECOVERED_ASCENDING -> {
                setSorting(ComparatorRecoveredAsc(this), AppConstants.RECOVERED_ASCENDING)
                recoveredAscending.setColorFilter(resources.getColor(R.color.red))
            }
            AppConstants.RECOVERED_DESCENDING -> {
                setSorting(ComparatorRecoveredDesc(this), AppConstants.RECOVERED_DESCENDING)
                recoveredDescending.setColorFilter(resources.getColor(R.color.red))
            }

        }
    }

    private fun setSorting(comparator: Comparator<Country>, sortingText: String) {
        var filterModel = statisticsViewModel?.getFilterModel()?.value
        var dataList = statisticsViewModel?.getCountryList()
        dataList?.sortWith(comparator)
        statisticsViewModel?.setCountryList(dataList!!)
        filterModel?.lastSort = filterModel!!.currentSort
        filterModel?.currentSort = sortingText
        statisticsViewModel?.setFiltermodel(filterModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_statistics)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
                        AppConstants.PERMISSION_LOCATION);

            } else {
                initObjects()
                if (commonUtilities.isNetworkConnected(this))
                    loadData()
                else {
                    setNoData("No Internet Connectivity")
                }
                setListener()
            }
        } else {
            initObjects()
            if (commonUtilities.isNetworkConnected(this))
                loadData()
            else {
                setNoData("No Internet Connectivity")
            }
            setListener()
        }


    }

    private fun setNoData(txtNoData: String) {

        txtNoResult.text = txtNoData
        txtNoResult.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun loadData() {
        statisticsViewModel = ViewModelProviders.of(this@CovidStatisticsActivity)
                .get(StatisticsViewModel::class.java)
        statisticsViewModel?.getLiveCountryList()?.observe(this, Observer<StatisticsResponse> { t: StatisticsResponse? ->

            var countries = t?.Countries
            countries = countries?.filter { s -> s.TotalConfirmed > 0 } as ArrayList<Country>
            statisticsViewModel?.setCountryList(countries)
            progressBar.visibility = View.GONE
            if (statisticsViewModel?.getFilterModel()?.value == null) {
                setListData(countries)
            } else {
                refreshList(statisticsViewModel?.getFilterModel()?.value!!.currentSort, statisticsViewModel?.getFilterModel()?.value!!.lastSort)
            }
        })

        statisticsViewModel?.getFilterModel()?.observe(this, Observer<FilterModel> { t: FilterModel? ->
            applyFilter(t)
        })

    }

    private fun applyFilter(filtermodel: FilterModel?) {
        var filteredList = statisticsViewModel?.getCountryList()
        filteredList = filteredList?.filter { s -> s.TotalConfirmed >= filtermodel!!.selectedminTotalCases && s.TotalConfirmed <= filtermodel!!.selectedmaxTotalCases } as ArrayList<Country>
        filteredList = filteredList?.filter { s ->
            s.TotalDeaths >= filtermodel!!.selectedminDeath && s.TotalDeaths <= filtermodel!!.selectedmaxDeath
        } as ArrayList<Country>
        filteredList = filteredList?.filter { s ->
            s.TotalRecovered >= filtermodel!!.selectedminRecovered && s.TotalRecovered <= filtermodel!!.selectedmaxRecovered
        } as ArrayList<Country>
        (countryList.adapter as CountryListAdapter).countries = filteredList
        (countryList.adapter as CountryListAdapter).notifyDataSetChanged()
        if (filteredList?.size == 0)
            txtNoResult.visibility = View.VISIBLE
        else
            txtNoResult.visibility = View.GONE
        totalCase.text = "Total Cases\n" + filteredList!!.sumBy { it.TotalConfirmed }.toString()
        deaths.text = "Deaths\n" + filteredList!!.sumBy { it.TotalDeaths }.toString()
        recovered.text = "Recovered\n" + filteredList!!.sumBy { it.TotalRecovered }.toString()
        setAppliedFilters(filtermodel)
    }

    private fun setAppliedFilters(filtermodel: FilterModel?) {
        var filterCount = 0;
        if (filtermodel!!.selectedminTotalCases != filtermodel!!.minTotalCases || filtermodel!!.selectedmaxTotalCases != filtermodel.maxTotalCases) {
            totalCaseFilter.text = "Total Cases " + filtermodel!!.selectedminTotalCases + " to " + filtermodel!!.selectedmaxTotalCases
            filterCount++
            totalCaseFilter.visibility = View.VISIBLE
        } else
            totalCaseFilter.visibility = View.GONE
        if (filtermodel!!.selectedminDeath != filtermodel!!.minDeath || filtermodel!!.selectedmaxDeath != filtermodel.maxDeath) {
            deathsFilter.text = "Deaths " + filtermodel!!.selectedminDeath + " to " + filtermodel!!.selectedmaxDeath
            filterCount++
            deathsFilter.visibility = View.VISIBLE
        } else
            deathsFilter.visibility = View.GONE

        if (filtermodel!!.selectedminRecovered != filtermodel!!.minRecovered || filtermodel!!.selectedmaxRecovered != filtermodel.maxRecovered) {
            filterCount++
            recoveredFilter.text = "Recovered " + filtermodel!!.selectedminRecovered + " to " + filtermodel!!.selectedmaxRecovered
            recoveredFilter.visibility = View.VISIBLE
        } else
            recoveredFilter.visibility = View.GONE
        if (filterCount > 0)
            appliedFilters.visibility = View.VISIBLE
        else
            appliedFilters.visibility = View.GONE
    }

    private fun setListData(countries: ArrayList<Country>) {
        totalCase.text = "Total Cases\n" + countries!!.sumBy { it.TotalConfirmed }.toString()
        deaths.text = "Deaths\n" + countries!!.sumBy { it.TotalDeaths }.toString()
        recovered.text = "Recovered\n" + countries!!.sumBy { it.TotalRecovered }.toString()
        casesDescending.setColorFilter(resources.getColor(R.color.red))
        countries.sortWith(ComparatorCasesDesc(this))
        countryList.adapter = CountryListAdapter(countries, this)
        countryList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var filterModel = FilterModel()
        filterModel.lastSort = ""
        filterModel.currentSort = AppConstants.CASES_DESCENDING
        filterModel.maxTotalCases = countries.maxBy { it.TotalConfirmed }!!.TotalConfirmed
        filterModel.maxDeath = countries.maxBy { it.TotalDeaths }!!.TotalDeaths
        filterModel.maxRecovered = countries.maxBy { it.TotalRecovered }!!.TotalRecovered
        filterModel.minTotalCases = countries.minBy { it.TotalConfirmed }!!.TotalConfirmed
        filterModel.minDeath = countries.minBy { it.TotalDeaths }!!.TotalDeaths
        filterModel.minRecovered = countries.minBy { it.TotalRecovered }!!.TotalRecovered
        filterModel.selectedminRecovered = countries.minBy { it.TotalRecovered }!!.TotalRecovered
        filterModel.selectedmaxRecovered = countries.maxBy { it.TotalRecovered }!!.TotalRecovered
        filterModel.selectedmaxDeath = countries.maxBy { it.TotalDeaths }!!.TotalDeaths
        filterModel.selectedminDeath = countries.minBy { it.TotalDeaths }!!.TotalDeaths
        filterModel.selectedminTotalCases = countries.minBy { it.TotalConfirmed }!!.TotalConfirmed
        filterModel.selectedmaxTotalCases = countries.maxBy { it.TotalConfirmed }!!.TotalConfirmed
        statisticsViewModel?.setFiltermodel(filterModel)
    }

    private fun initObjects() {
        commonUtilities.getCountry(this)
        val listparam: RelativeLayout.LayoutParams = countryList.layoutParams as RelativeLayout.LayoutParams
        listparam.height = commonUtilities.getDeviceHeight(this) / 2 - 170
        countryList.layoutParams = listparam
        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                progressBar.visibility = View.VISIBLE
                txtLoading.text = "refreshing.."
                statisticsViewModel?.getLiveCountryList()
                handler.postDelayed(this, AppConstants.TWO_MINUTES) //now is every 2 minutes
            }
        }, AppConstants.TWO_MINUTES)
    }

    private fun removePreviousTint(lastSort: String) {
        when (lastSort) {
            AppConstants.COUNTRY_ASCENDING -> {
                countryAscending.setColorFilter(0)
            }
            AppConstants.COUNTRY_DESCENDING -> {
                countryDescending.setColorFilter(0)
            }
            AppConstants.CASES_ASCENDING -> {
                casesAscending.setColorFilter(0)
            }
            AppConstants.CASES_DESCENDING -> {
                casesDescending.setColorFilter(0)
            }
            AppConstants.DEATHS_ASCENDING -> {
                deathsAscending.setColorFilter(0)
            }
            AppConstants.DEATHS_DESCENDING -> {
                deathsDescending.setColorFilter(0)
            }
            AppConstants.RECOVERED_ASCENDING -> {
                recoveredAscending.setColorFilter(0)
            }
            AppConstants.RECOVERED_DESCENDING -> {
                recoveredDescending.setColorFilter(0)
            }
        }


    }

    private fun setListener() {
        countryAscending.setOnClickListener(this)
        countryDescending.setOnClickListener(this)
        casesAscending.setOnClickListener(this)
        casesDescending.setOnClickListener(this)
        deathsDescending.setOnClickListener(this)
        deathsAscending.setOnClickListener(this)
        recoveredAscending.setOnClickListener(this)
        recoveredDescending.setOnClickListener(this)
        filter.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            AppConstants.PERMISSION_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initObjects()
                    if (commonUtilities.isNetworkConnected(this))
                        loadData()
                    else {
                        setNoData("No Internet Connectivity")
                    }
                    setListener()
                } else {
                    progressBar.visibility = View.GONE
                    txtNoResult.visibility = View.VISIBLE
                    Toast.makeText(this, "Permission required", Toast.LENGTH_LONG).show()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }
}
