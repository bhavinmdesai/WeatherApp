package dev.bhavindesai.weatherapp.di.modules

import dagger.Module
import dagger.Provides
import dev.bhavindesai.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @[Provides Singleton]
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @[Provides Singleton]
    fun providesOkHttp(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .build()

    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
}
