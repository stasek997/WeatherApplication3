package com.weatherapplication.repository

import com.weatherapplication.data.City
import com.weatherapplication.data.WeatherModelDTO


interface WeatherRepository {

    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherModelDTO>)

    }

