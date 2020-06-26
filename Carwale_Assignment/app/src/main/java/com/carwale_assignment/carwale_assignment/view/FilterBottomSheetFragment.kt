package com.carwale_assignment.carwale_assignment.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.carwale_assignment.carwale_assignment.R
import kotlinx.android.synthetic.main.filter_bottom_sheet.view.*
import org.florescu.android.rangeseekbar.RangeSeekBar


class FilterBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.applyFilter -> {
                val filterModel = (activity as CovidStatisticsActivity).statisticsViewModel?.getFilterModel()?.value
                filterModel?.selectedmaxDeath = deathSeekbar?.selectedMaxValue!!
                filterModel?.selectedminDeath = deathSeekbar?.selectedMinValue!!
                filterModel?.selectedminTotalCases = totalCasesSeekbar?.selectedMinValue!!
                filterModel?.selectedmaxTotalCases = totalCasesSeekbar?.selectedMaxValue!!
                filterModel?.selectedminRecovered = recoveredSeekbar?.selectedMinValue!!
                filterModel?.selectedmaxRecovered = recoveredSeekbar?.selectedMaxValue!!
                (activity as CovidStatisticsActivity).statisticsViewModel?.setFiltermodel(filterModel!!)
                dismiss()

            }
            R.id.resetFilter -> {
                val filterModel = (activity as CovidStatisticsActivity).statisticsViewModel?.getFilterModel()?.value
                filterModel?.selectedmaxDeath = filterModel!!.maxDeath
                filterModel?.selectedminDeath = filterModel!!.minDeath
                filterModel?.selectedmaxTotalCases = filterModel!!.maxTotalCases
                filterModel?.selectedminTotalCases = filterModel!!.minTotalCases
                filterModel?.selectedmaxRecovered = filterModel!!.maxRecovered
                filterModel?.selectedminRecovered = filterModel!!.minRecovered
                (activity as CovidStatisticsActivity).statisticsViewModel?.setFiltermodel(filterModel!!)
                dismiss()

            }
            R.id.closeDialog -> {
                dismiss()
            }
        }
    }

    //Bottom Sheet Callback
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }

        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    private var totalCasesSeekbar: RangeSeekBar<Int>? = null

    private var deathSeekbar: RangeSeekBar<Int>? = null

    private var recoveredSeekbar: RangeSeekBar<Int>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeFilter()
        setListener()
    }

    private fun setListener() {
        contentView!!.applyFilter.setOnClickListener(this)
        contentView!!.resetFilter.setOnClickListener(this)
        contentView!!.closeDialog.setOnClickListener(this)
    }

    private fun initializeFilter() {
        var filtermodel = (activity as CovidStatisticsActivity).statisticsViewModel?.getFilterModel()?.value

        val totalCasesHead = LayoutInflater.from(context).inflate(R.layout.filter_head, null)
        (totalCasesHead as TextView).text = "Total Cases"
        contentView!!.lnrRange.addView(totalCasesHead)
        totalCasesSeekbar = RangeSeekBar<Int>(activity)
        // Set the range
        totalCasesSeekbar?.setRangeValues(filtermodel?.minTotalCases!!, filtermodel?.maxTotalCases!!)
        totalCasesSeekbar?.setSelectedMinValue(filtermodel?.selectedminTotalCases!!)
        totalCasesSeekbar?.setSelectedMaxValue(filtermodel?.selectedmaxTotalCases!!)
        totalCasesSeekbar?.setTextAboveThumbsColor(activity?.resources!!.getColor(R.color.black))
        contentView!!.lnrRange.addView(totalCasesSeekbar)


        val deathHead = LayoutInflater.from(context).inflate(R.layout.filter_head, null)
        (deathHead as TextView).text = "Deaths"
        contentView!!.lnrRange.addView(deathHead)
        deathSeekbar = RangeSeekBar<Int>(activity)
        // Set the range
        deathSeekbar?.setRangeValues(filtermodel?.minDeath!!, filtermodel?.maxDeath!!)
        deathSeekbar?.setSelectedMinValue(filtermodel?.selectedminDeath)
        deathSeekbar?.setSelectedMaxValue(filtermodel?.selectedmaxDeath)
        deathSeekbar?.setTextAboveThumbsColor(activity?.resources!!.getColor(R.color.black))
        contentView!!.lnrRange.addView(deathSeekbar)

        val recoveredHead = LayoutInflater.from(context).inflate(R.layout.filter_head, null)
        (recoveredHead as TextView).text = "Recovered"
        contentView!!.lnrRange.addView(recoveredHead)

        recoveredSeekbar = RangeSeekBar<Int>(activity)
        // Set the range
        recoveredSeekbar?.setRangeValues(filtermodel?.minRecovered!!, filtermodel?.maxRecovered!!)
        recoveredSeekbar?.setSelectedMinValue(filtermodel?.selectedminRecovered)
        recoveredSeekbar?.setSelectedMaxValue(filtermodel?.selectedmaxRecovered)
        recoveredSeekbar?.setTextAboveThumbsColor(activity?.resources!!.getColor(R.color.black))
        contentView!!.lnrRange.addView(recoveredSeekbar)

    }

    private var contentView: View? = null

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        //Get the content View
        contentView = View.inflate(context, R.layout.filter_bottom_sheet, null)
        dialog.setContentView(contentView)

        //Set the coordinator layout behavior
        val params = (contentView?.getParent() as View).getLayoutParams() as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        //Set callback
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

    }
}