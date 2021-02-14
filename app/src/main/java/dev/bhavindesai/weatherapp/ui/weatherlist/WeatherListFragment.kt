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

        viewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.showWeatherList.observe(viewLifecycleOwner) {
            binding.rvWeatherList.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.showNoInternet.observe(viewLifecycleOwner) {
            binding.lblNoInternet.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.cityName.observe(viewLifecycleOwner) {
            binding.txtCityName.text = it
        }

        viewModel.weatherForecast.observe(viewLifecycleOwner) {
            it?.let {
                binding.rvWeatherList.adapter = WeatherListAdapter(it).apply {
                    itemClickListener = this@WeatherListFragment
                }
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        val direction = WeatherListFragmentDirections.goToDetailFragment(weather.woeid, weather.applicable_date)
        findNavController().navigate(direction)
    }
}