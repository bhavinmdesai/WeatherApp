package dev.bhavindesai.weatherapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dev.bhavindesai.weatherapp.WeatherApplication
import dev.bhavindesai.weatherapp.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityModule::class,
        AndroidInjectionModule::class,
        ApiModule::class,
        FragmentModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        RepositoriesModule::class,
        DatabaseModule::class,
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApplication)
}
