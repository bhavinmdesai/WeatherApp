package dev.bhavindesai.weatherapp.ui.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.bhavindesai.domain.local.Weather
import dev.bhavindesai.viewmodels.WeatherListViewModel
import dev.bhavindesai.weatherapp.databinding.WeatherListFragmentBinding
import dev.bhavindesai.weatherapp.ui.base.BaseFragment
import kotlinx.coroutines.FlowPreview

class WeatherListFragment : BaseFragment(), WeatherListAdapter.OnItemClickListener {

    private val viewModel: WeatherListViewModel by lazyViewModel()
    private lateinit var binding: WeatherListFragmentBinding

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchWeather()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherForecast.observe(viewLifecycleOwner) { locationWeatherData ->
            binding.progressBar.visibility = View.GONE
            if (locationWeatherData != null) {
                binding.groupSuccessfulResponse.visibility = View.VISIBLE
                binding.groupUnsuccessfulResponse.visibility = View.GONE

                binding.txtCityName.text = locationWeatherData.locationData.title
                binding.rvWeatherList.adapter = WeatherListAdapter(locationWeatherData).apply {
                    itemClickListener = this@WeatherListFragment
                }
            } else {
                binding.groupSuccessfulResponse.visibility = View.GONE
                binding.groupUnsuccessfulResponse.visibility = View.VISIBLE
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        val direction = WeatherListFragmentDirections.goToDetailFragment(weather.woeid, weather.applicable_date)
        findNavController().navigate(direction)
    }
}