package dev.bhavindesai.data

class WeatherRepository {

    suspend fun getWeatherForCity(city: String) {
        val woeId = getWhereOnEarthId(city)

        getWeatherForWoeId(woeId)
    }

    private suspend fun getWhereOnEarthId(city: String): Long {
        return 44418
    }

    private suspend fun getWeatherForWoeId(woeId: Long) {

    }
}