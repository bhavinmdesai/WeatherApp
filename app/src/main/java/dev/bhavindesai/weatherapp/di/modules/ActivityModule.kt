package dev.bhavindesai.weatherapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.bhavindesai.weatherapp.ui.main.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
