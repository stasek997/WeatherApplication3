package com.weatherapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hellohasan.weatherappmvvm.utils.kelvinToCelsius
import com.hellohasan.weatherappmvvm.utils.unixTimestampToDateTimeString
import com.hellohasan.weatherappmvvm.utils.unixTimestampToTimeString
import com.weatherapplication.data.City
import com.weatherapplication.data.WeatherData
import com.weatherapplication.data.WeatherModelDTO
import com.weatherapplication.repository.RequestCompleteListener
import com.weatherapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(
    val repository: WeatherRepository
) : ViewModel() {

    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()


    fun getCityList() {
        repository.getCityList(object : RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                cityListLiveData.postValue(data) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                cityListFailureLiveData.postValue(errorMessage)
                // PUSH error message to LiveData object
            }
        })
    }


}
