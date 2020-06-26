package com.carwale_assignment.carwale_assignment.view

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.carwale_assignment.carwale_assignment.R
import com.carwale_assignment.carwale_assignment.model.Country
import com.carwale_assignment.carwale_assignment.utility.PrefManager
import kotlinx.android.synthetic.main.statistics_list_item.view.*

class CountryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var countries: ArrayList<Country>? = null
    private var activity: Activity

    constructor(countries: ArrayList<Country>, activity: Activity) {
        this.countries = countries
        this.activity = activity
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.statistics_list_item, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.setLayoutParams(lp)
        return CountryViewHolder(view, activity)
    }

    override fun getItemCount(): Int {
        return countries!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CountryViewHolder)
            holder.bindItems(countries!![position])
    }

    class CountryViewHolder(itemView: View, mactivity: Activity) : RecyclerView.ViewHolder(itemView) {
        val activity: Activity = mactivity
        fun bindItems(country: Country) {
            if (country.Country.equals(PrefManager().getUserCountry(activity), true)) {
                itemView.countryName.text = Html.fromHtml("<b>" + country.Country + "</b>")
                itemView.totalCase.text = Html.fromHtml("<b>" + country.TotalConfirmed.toString() + "</b>")
                itemView.recovered.text = Html.fromHtml("<b>" + country.TotalRecovered.toString() + "</b>")
                itemView.deaths.text = Html.fromHtml("<b>" + country.TotalDeaths.toString() + "</b>")
            } else {
                itemView.countryName.text = country.Country
                itemView.totalCase.text = country.TotalConfirmed.toString()
                itemView.recovered.text = country.TotalRecovered.toString()
                itemView.deaths.text = country.TotalDeaths.toString()
            }
        }
    }
}