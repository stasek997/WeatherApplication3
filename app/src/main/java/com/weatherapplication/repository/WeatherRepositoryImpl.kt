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


class WeatherRepositoryImpl constructor (private val context: WeatherApp, private val apiInterface: WeatherApiInterface): WeatherRepository{

    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents  = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!) //let presenter know about failure
        }
    }

    override fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherModelDTO>) {


        val call: Call<WeatherModelDTO> = apiInterface.getWeather(cityId)

        call.enqueue(object : Callback<WeatherModelDTO> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(call: Call<WeatherModelDTO>, response: Response<WeatherModelDTO>) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherModelDTO>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }
}










