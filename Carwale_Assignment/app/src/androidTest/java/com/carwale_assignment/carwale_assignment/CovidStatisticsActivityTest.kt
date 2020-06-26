package com.carwale_assignment.carwale_assignment

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.TextView
import com.carwale_assignment.carwale_assignment.utility.CommonUtilities
import com.carwale_assignment.carwale_assignment.view.CovidStatisticsActivity
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CovidStatisticsActivityTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(CovidStatisticsActivity::class.java)
    var covidStatisticsActivity: CovidStatisticsActivity? = null

    @Test
    fun testLaunch() {
        onView(withId(R.id.filter)).perform(click())
    }



}