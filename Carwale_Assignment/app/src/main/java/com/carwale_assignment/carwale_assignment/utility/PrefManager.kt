package com.carwale_assignment.carwale_assignment.utility

import android.R.id.edit
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE


class PrefManager {
    fun saveCountry(countryName: String, activity: Activity) {
        val sharedPreferences = activity.getSharedPreferences("user_details", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("countryName", countryName)
        editor.apply()
    }

    fun getUserCountry(activity: Activity): String {

        val sharedPreferences = activity.getSharedPreferences("user_details", Context.MODE_PRIVATE)
        return sharedPreferences.getString("countryName", "")
    }
}