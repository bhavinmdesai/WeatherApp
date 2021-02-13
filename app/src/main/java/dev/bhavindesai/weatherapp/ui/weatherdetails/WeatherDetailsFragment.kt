package dev.bhavindesai.weatherapp.ui.weatherdetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dev.bhavindesai.domain.remote.WeatherResponse
import dev.bhavindesai.viewmodels.WeatherDetailsViewModel
import dev.bhavindesai.weatherapp.R
import dev.bhavindesai.weatherapp.databinding.WeatherDetailFragmentBinding
import dev.bhavindesai.weatherapp.ui.base.BaseFragment


class WeatherDetailsFragment : BaseFragment() {

    private val viewModel: WeatherDetailsViewModel by lazyViewModel()

    private lateinit var binding: WeatherDetailFragmentBinding
    private lateinit var chart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChart()

        val woeId = arguments?.getLong("woeId")
        val date = arguments?.getString("date")

        if (woeId!=null && date!=null) {
            viewModel
                .fetchWeatherDetailsOfDay(woeId, date)
                .observe(viewLifecycleOwner) {
                    if (it != null)
                        setData(it)
                }
        }
    }

    private fun initChart() {
        chart = binding.chart

        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        val xAxis = chart.xAxis

        chart.axisRight.isEnabled = false

        val yAxis = chart.axisLeft
        yAxis.axisMaximum = 20f
        yAxis.axisMinimum = -10f

        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f

        val ll1 = LimitLine(150f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f

        val ll2 = LimitLine(-30f, "Lower Limit")
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis.addLimitLine(ll1)
        yAxis.addLimitLine(ll2)

        // draw points over time
        chart.animateX(1500)

        // get the legend (only possible after setting data)
        chart.legend.form = Legend.LegendForm.LINE
    }

    private fun setData(list: List<WeatherResponse>) {

        val values = mutableListOf<Entry>()

        list.filterIndexed { index, _ ->
            index % 3 == 0
        }.forEachIndexed { index, weatherResponse ->
            values.add(Entry(index.toFloat(), weatherResponse.the_temp.toFloat()))
        }

        val set1: LineDataSet
        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "Humidity")
            set1.setDrawIcons(true)

            // black lines and points
            set1.color = Color.BLUE
            set1.setCircleColor(Color.BLUE)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.setDrawCircleHole(false)

            // text size of values
            set1.valueTextSize = 12f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }

            // drawables only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
            set1.fillDrawable = drawable

            val dataSets = mutableListOf<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data

            chart.invalidate()
        }
    }
}