# Weather App

This is a small app that fetches data from the [MetaWeather](https://www.metaweather.com)'s public API and show weather information for 5 days including today. Tapping on the day shows temperature chart over the course of the day.

# Architecture

The app uses a lot of things from modern android development like:
- Kotlin
- Coroutines with Flow
- MVVM 
- Material Components
- Data Binding
- Multi Module
- Jetpack Navigation with Safe Args
- Room Database for persistence
- Dagger2 for dependency injection

Some of the third party libraries that have been used for the ease of development:
- Fresco for Image loading
- MPAndroidChart for charting
- Mockk for mocking objects
- Retrofit, OkHttp and GSON for network communication and serialization

## Areas of improvements

For the simplicity and focus more on the architecture and testability, I have not focused on some of the areas which can be improved. Here are some of them:

1. Rather than  setting hard-coded location of London, it can be dynamic by requesting user's approximate location and fetch weather for the user
2. When there is no internet, a retry button should be provided which can try fetching the data again.
3. There can be a `dependencies.gradle` where all the version's are mentioned and they can be managed from the central place. In future, it can be replaced with Kotlin DSL.
4. Unit test case coverage can be extended to `WeatherDetailsViewModel` and `WeatherRepository`

## Screenshots for quick reference

![List screen](/screenshots/list-screen.png?raw=true "List screen")
![Detail screen](/screenshots/detail-screen.png?raw=true "Detail screen")
