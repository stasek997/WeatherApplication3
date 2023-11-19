package com.weatherapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.data.City
import com.weatherapplication.repository.WeatherPref
import com.weatherapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(
    val repository: WeatherRepository,
    private val prefs: WeatherPref
) : ViewModel() {

    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()


    fun getCityList() = viewModelScope.launch {

        try {

            val data = repository.getCityList()
            cityListLiveData.postValue(data)
        }

        catch (e:Exception) {
            val errorMessage = e.localizedMessage.orEmpty()
            cityListFailureLiveData.postValue(errorMessage)
        }

        catch (e: CancellationException){
            val errorMessage = e.localizedMessage.orEmpty()
            cityListFailureLiveData.postValue(errorMessage)
        }
    }


    var cityId: City?
        get () = prefs.city
        set(value) {
            prefs.city = value
        }

    var dayNightMode: Int
        get () = prefs.dayNightMode
        set(value) {
            prefs.dayNightMode = value
        }

}
