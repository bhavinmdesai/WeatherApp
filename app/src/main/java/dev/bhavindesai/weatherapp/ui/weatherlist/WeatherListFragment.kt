package dev.bhavindesai.weatherapp.ui.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import dev.bhavindesai.weatherapp.R
import dev.bhavindesai.weatherapp.ui.base.BaseFragment

class WeatherListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_list_fragment, container, false)
    }
}