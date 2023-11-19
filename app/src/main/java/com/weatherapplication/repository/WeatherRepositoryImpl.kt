package com.weatherapplication.repository


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.weatherapplication.WeatherApp
import com.weatherapplication.data.City
import com.weatherapplication.data.WeatherModelDTO
import com.weatherapplication.network.WeatherApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class WeatherRepositoryImpl constructor (
    private val context: WeatherApp,
    private val apiInterface: WeatherApiInterface
): WeatherRepository {

    override suspend fun getCityList(): MutableList<City> {

            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            return cityList//let presenter know the city list

    }

    override suspend fun getWeatherInfo(cityId: Int): WeatherModelDTO = apiInterface.getWeather(cityId)


}







