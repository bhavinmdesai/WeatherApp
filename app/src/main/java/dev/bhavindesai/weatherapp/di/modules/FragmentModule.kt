package dev.bhavindesai.weatherapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.bhavindesai.weatherapp.ui.weatherdetails.WeatherDetailsFragment
import dev.bhavindesai.weatherapp.ui.weatherlist.WeatherListFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindWeatherListFragment(): WeatherListFragment

    @ContributesAndroidInjector
    abstract fun bindWeatherDetailsFragment(): WeatherDetailsFragment
}
