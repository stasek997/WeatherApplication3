package com.weatherapplication.network

import com.weatherapplication.data.WeatherModelDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiInterface {


    @GET("/data/2.5/weather")
   suspend fun getWeather(
        @Query("id") cityId: Int,
        @Query("appid") appId: String = "5404e1cc75e98a523480814e190b72fe"
    ): WeatherModelDTO

}



