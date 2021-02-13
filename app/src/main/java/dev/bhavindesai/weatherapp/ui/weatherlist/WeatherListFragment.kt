package dev.bhavindesai.weatherapp.ui.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.bhavindesai.viewmodels.WeatherListViewModel
import dev.bhavindesai.weatherapp.R
import dev.bhavindesai.weatherapp.ui.base.BaseFragment

class WeatherListFragment : BaseFragment() {

    private val viewModel: WeatherListViewModel by lazyViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherForecast.observe(viewLifecycleOwner) {
            println(it)
        }
    }
}