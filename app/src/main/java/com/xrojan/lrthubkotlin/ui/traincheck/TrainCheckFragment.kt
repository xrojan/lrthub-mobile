package com.xrojan.lrthubkotlin.ui.traincheck

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.fragments.BaseFragment
import com.xrojan.lrthubkotlin.viewmodel.TrainCheckViewModel
import kotlinx.android.synthetic.main.traincheck_fragment.*
import android.graphics.Color
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import android.util.Log
import com.github.mikephil.charting.data.BarData
import com.xrojan.lrthubkotlin.repository.entities.TrainCheckHistory
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.schedulers.Schedulers
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter


/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class TrainCheckFragment : BaseFragment() {
    private val trainCheckViewModel: TrainCheckViewModel = App.injectTrainCheckViewModel()

    companion object {
        fun newInstance() = TrainCheckFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.traincheck_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        subscribe(trainCheckViewModel.getTrainCheckFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    onSuccessChart(it)
                }, {
                    Log.e(tag, it.message)
                }))
    }

    private fun onSuccessChart(data: UIData<List<TrainCheckHistory>>) {
        doAsync {
            uiThread {
                // Init chart
                val trainCheckHistory = data.request.result
                val entries = ArrayList<BarEntry>()
                var xLabel = arrayListOf<String>()

                for (i in 0 until trainCheckHistory.size) {
                    entries.add(BarEntry(i.toFloat(), trainCheckHistory[i].countFaces.toFloat()))
                    xLabel.add(trainCheckHistory[i].createdOn)
                }

                val d = BarDataSet(entries, "Train Check History")
                d.barShadowColor = Color.rgb(203, 203, 203)

                val sets = ArrayList<IBarDataSet>()
                sets.add(d)

                val cd = BarData(sets)
                cd.barWidth = 0.9f

                // apply styling
                d.valueTextColor = Color.BLACK
                bc_traincheck.description.isEnabled = false

                bc_traincheck.setDrawGridBackground(false)

                val xAxis = bc_traincheck.xAxis
                xAxis.position = XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.setAvoidFirstLastClipping(true)
                xAxis.granularity = 1f
                xAxis.labelCount = xLabel.count() /2
                xAxis.valueFormatter = IAxisValueFormatter { value, axis -> xLabel.get(value.toInt()) }

                val leftAxis = bc_traincheck.axisLeft
                leftAxis.setLabelCount(5, false)
                leftAxis.spaceTop = 15f

                val rightAxis = bc_traincheck.getAxisRight()
                rightAxis.setLabelCount(5, false)
                rightAxis.spaceTop = 15f

                // set data
                bc_traincheck.data = cd
                bc_traincheck.setFitBars(true)
                bc_traincheck.animateY(700)
            }
        }
    }

}
