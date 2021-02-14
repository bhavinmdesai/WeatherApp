package dev.bhavindesai.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.bhavindesai.data.local.WeatherDataDao
import dev.bhavindesai.data.remote.WeatherService
import dev.bhavindesai.data.repositories.WeatherRepository
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.domain.local.Location
import dev.bhavindesai.domain.local.LocationWeatherData
import dev.bhavindesai.domain.remote.LocationResponse
import dev.bhavindesai.domain.remote.WhereOnEarth
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import java.util.concurrent.Executors

class WeatherListViewModelTest {

    @Rule @JvmField
    val rule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Test @FlowPreview
    fun `verify view model states when data is emitted`() {
        withMocks {
            viewModel.weatherForecast.observeForever {
                Assert.assertNotNull(it)
            }

            viewModel.showProgress.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.cityName.observeForever {
                Assert.assertEquals("Pune", it)
            }
        }
    }

    @Test @FlowPreview
    fun `verify view model states when no internet on first load`() {
        val internetUtil = mockk<InternetUtil>()
        internetUtil.withNoInternetConnection()

        withMocks(fixture = {Fixture(internetUtil = internetUtil)}) {
            viewModel.weatherForecast.observeForever {
                Assert.assertNull(it)
            }

            viewModel.showProgress.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertTrue(it)
            }

            viewModel.cityName.observeForever {
                Assert.assertEquals("", it)
            }
        }
    }

    @Test @FlowPreview
    fun `verify view model states and stored data when no internet`() {
        val internetUtil = mockk<InternetUtil>()
        internetUtil.withNoInternetConnection()

        val weatherDataDao = mockk<WeatherDataDao>()
        weatherDataDao.withNonEmptyDatabase()

        withMocks(fixture = {Fixture(
            internetUtil = internetUtil,
            weatherDataDao = weatherDataDao
        )}) {
            viewModel.weatherForecast.observeForever {
                Assert.assertNotNull(it)
            }

            viewModel.showProgress.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.showNoInternet.observeForever {
                Assert.assertFalse(it)
            }

            viewModel.cityName.observeForever {
                Assert.assertEquals("Pune", it)
            }
        }
    }

    @Test @FlowPreview
    fun `verify view model data comes twice to the observer`() {
        val weatherDataDao = mockk<WeatherDataDao>()
        weatherDataDao.withNonEmptyDatabase()

        withMocks(fixture = {Fixture(
            weatherDataDao = weatherDataDao
        )}) {
            verify(exactly = 2) {
                observerLocationWeatherData.onChanged(any())
            }
        }
    }

    /** need to take in the fixture lazily so we can alter the scheduler before the fixture is created */
    @FlowPreview
    private fun withMocks(fixture: () -> Fixture = { Fixture() }, test: Fixture.() -> Unit) {
        val f = fixture()
        f.apply(test)
    }

    @FlowPreview
    private data class Fixture(
        val weatherDataDao: WeatherDataDao = mockk<WeatherDataDao>().apply {
            withEmptyDatabase()
        },
        val weatherService: WeatherService = mockk<WeatherService>().apply {
            withPuneWeather()
        },
        val internetUtil: InternetUtil = mockk<InternetUtil>().apply {
            withInternetConnection()
        },
        val weatherRepository: WeatherRepository = WeatherRepository(weatherService, weatherDataDao, internetUtil),
        val viewModel: WeatherListViewModel = WeatherListViewModel(weatherRepository),
        val observerLocationWeatherData: Observer<LocationWeatherData?> = mockk(),
    ) {
        init {
            viewModel.fetchWeather()

            every { observerLocationWeatherData.onChanged(any()) } just runs
            viewModel.weatherForecast.observeForever(observerLocationWeatherData)
        }
    }

    @ObsoleteCoroutinesApi @ExperimentalCoroutinesApi @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ObsoleteCoroutinesApi @ExperimentalCoroutinesApi @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}

private fun InternetUtil.withInternetConnection() {
    every { isInternetOn() } returns true
}

private fun InternetUtil.withNoInternetConnection() {
    every { isInternetOn() } returns false
}

private fun WeatherDataDao.withEmptyDatabase() {
    coEvery { getWhereOnEarth() } returns null
    coEvery { getLocation() } returns null

    coEvery { deleteAllWhereOnEarth() } just runs
    coEvery { deleteAllWeathers() } just runs
    coEvery { deleteAllLocations() } just runs

    coEvery { storeWhereOnEarth(any()) } just runs
    coEvery { storeWeather(any()) } just runs
    coEvery { storeLocation(any()) } just runs
}

private fun WeatherDataDao.withNonEmptyDatabase() {
    coEvery { getWhereOnEarth() } returns WhereOnEarth(
        "Pune",
        "City",
        1234,
        "1.25, 2.34"
    )
    coEvery { getLocation() } returns LocationWeatherData(
        Location("", "", "", "Pune", 1234),
        emptyList()
    )

    coEvery { deleteAllWhereOnEarth() } just runs
    coEvery { deleteAllWeathers() } just runs
    coEvery { deleteAllLocations() } just runs

    coEvery { storeWhereOnEarth(any()) } just runs
    coEvery { storeWeather(any()) } just runs
    coEvery { storeLocation(any()) } just runs
}

private fun WeatherService.withPuneWeather() {
    coEvery { getWhereOnEarth(any()) } returns listOf(WhereOnEarth(
        "Pune",
        "City",
        1234,
        "1.25, 2.34"
    ))

    coEvery { getWeatherData(any()) } returns LocationResponse(
        emptyList(), "", "", "", "Pune", 1234
    )
}
