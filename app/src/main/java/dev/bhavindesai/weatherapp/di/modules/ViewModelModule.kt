package dev.bhavindesai.weatherapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bhavindesai.weatherapp.di.DaggerViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import dev.bhavindesai.viewmodels.WeatherDetailsViewModel
import dev.bhavindesai.viewmodels.WeatherListViewModel
import kotlin.reflect.KClass

/** Annotation definition for manually creating ViewModel providers / bindings */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @[Binds IntoMap ViewModelKey(WeatherListViewModel::class)]
    internal abstract fun bindWeatherListViewModel(viewModel: WeatherListViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(WeatherDetailsViewModel::class)]
    internal abstract fun bindWeatherDetailsViewModel(viewModel: WeatherDetailsViewModel): ViewModel
}
