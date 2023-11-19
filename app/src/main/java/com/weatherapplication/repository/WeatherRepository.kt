package com.weatherapplication.repository

import com.weatherapplication.data.City
import com.weatherapplication.data.WeatherModelDTO


interface WeatherRepository {

    suspend fun getCityList(): MutableList<City>
    suspend fun getWeatherInfo(cityId: Int): WeatherModelDTO

    }

