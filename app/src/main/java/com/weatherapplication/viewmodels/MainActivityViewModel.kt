package com.weatherapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellohasan.weatherappmvvm.utils.kelvinToCelsius
import com.hellohasan.weatherappmvvm.utils.unixTimestampToDateTimeString
import com.hellohasan.weatherappmvvm.utils.unixTimestampToTimeString
import com.weatherapplication.data.City
import com.weatherapplication.data.WeatherData

import com.weatherapplication.repository.WeatherPref
import com.weatherapplication.repository.WeatherRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val prefs: WeatherPref
) : ViewModel() {


    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    init {
       val cashedCity = cityId
        if (cashedCity != null) {
            getWeatherInfo(cashedCity.id)

        }
    }
    fun getWeatherInfo(cityId: Int) = viewModelScope.launch(Dispatchers.Main){
        progressBarLiveData.postValue(true)
           withContext(Dispatchers.IO) {
               try {
                   val data = repository.getWeatherInfo(cityId)
                   val weatherData = WeatherData(
                       dateTime = data.dt.unixTimestampToDateTimeString(),
                       temperature = data.main.temp.kelvinToCelsius().toString(),
                       cityAndCountry = "${data.name}, ${data.sys.country}",
                       weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                       weatherConditionIconDescription = data.weather[0].description,
                       humidity = "${data.main.humidity}%",
                       pressure = "${data.main.pressure} mBar",
                       visibility = "${data.visibility / 1000.0} KM",
                       sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                       sunset = data.sys.sunset.unixTimestampToTimeString()
                   )

                   withContext(Dispatchers.Main){
                       progressBarLiveData.postValue(false) // PUSH data to LiveData object to hide progress bar

                       // After applying business logic and data manipulation, we push data to show on UI
                       weatherInfoLiveData.postValue(weatherData)
                   }

               }

               catch (e: Exception){

                   val errorMessage = e.localizedMessage.orEmpty()

                   progressBarLiveData.postValue(false) // hide progress bar
                   weatherInfoFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
               }

               catch (e: CancellationException){

                   val errorMessage = e.localizedMessage.orEmpty()

                   progressBarLiveData.postValue(false) // hide progress bar
                   weatherInfoFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
               }
           }

    }
    private val cityId: City?
        get () = prefs.city


    val dayNightMode: Int
        get () = prefs.dayNightMode


}