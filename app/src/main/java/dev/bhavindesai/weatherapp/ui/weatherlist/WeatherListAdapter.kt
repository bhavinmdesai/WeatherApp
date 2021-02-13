package dev.bhavindesai.weatherapp.ui.weatherlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bhavindesai.domain.local.LocationWeatherData
import dev.bhavindesai.domain.local.Weather
import dev.bhavindesai.weatherapp.databinding.WeatherListItemBinding

class WeatherListAdapter(
    private val locationWeatherData: LocationWeatherData
) : RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(weather: Weather)
    }

    class WeatherListViewHolder(
        private val binding: WeatherListItemBinding,
        private val itemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            binding.root.setOnClickListener { itemClickListener?.onItemClick(weather) }
            binding.weather = weather
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder =
        WeatherListViewHolder(
            WeatherListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) =
        holder.bind(locationWeatherData.weatherData[position])

    override fun getItemCount() =
        locationWeatherData.weatherData.size
}