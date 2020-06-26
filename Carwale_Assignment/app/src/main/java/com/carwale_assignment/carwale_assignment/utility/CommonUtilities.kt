package com.carwale_assignment.carwale_assignment.utility

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.location.Geocoder
import android.content.Context.LOCATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.location.LocationManager
import java.io.IOException
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager




class CommonUtilities {
    fun getDeviceHeight(activity: Activity):Int{
        val displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getCountry(activity: Activity){

        var country_name: String? = null
        val lm = activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val geocoder = Geocoder(activity.getApplicationContext())
        for (provider in lm.allProviders) {
            val location = lm.getLastKnownLocation(provider)
            if (location != null) {
                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses != null && addresses.size > 0) {
                        val prefManager =PrefManager()
                        country_name = addresses[0].countryName
                        prefManager.saveCountry(country_name,activity)
                        break
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }


     fun isNetworkConnected(activity: Activity): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

}