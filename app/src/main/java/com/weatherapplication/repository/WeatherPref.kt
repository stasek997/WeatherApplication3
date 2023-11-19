package com.weatherapplication.repository

import android.app.UiModeManager.MODE_NIGHT_NO
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.weatherapplication.data.City

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val PREFS_CITY_ID_KEY = "prefs_city_id_key"
const val PREF_TAG = "prefs_tag"

const val PREFS_DAY_NIGHT_ID_KEY = "prefs_day_night_id_key"


@Singleton
class WeatherPref @Inject constructor(@ApplicationContext context : Context) {
    private val prefs = context.getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE)
    private val gson = Gson()

    var city: City?
        get() {
            val json = prefs.getString(PREFS_CITY_ID_KEY, null)
            return json?.let {
                gson.fromJson(json, City::class.java)
            }
        }
            set(value) {
                prefs.edit()
                    .putString(PREFS_CITY_ID_KEY, gson.toJson(value))
                    .apply()
            }

    var dayNightMode: Int
        get() = prefs.getInt(PREFS_DAY_NIGHT_ID_KEY, MODE_NIGHT_NO)
        set(value) {
            prefs.edit()
                .putInt(PREFS_DAY_NIGHT_ID_KEY, value)
                .apply()
        }



}