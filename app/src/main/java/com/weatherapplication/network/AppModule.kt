package com.weatherapplication.network

import android.content.Context
import com.weatherapplication.WeatherApp
import com.weatherapplication.repository.WeatherRepository
import com.weatherapplication.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides

    fun provideBaseUrl(): String = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides

    fun provideWeatherApiInterface(retrofit: Retrofit): WeatherApiInterface {
        return retrofit.create(WeatherApiInterface::class.java)
    }

   @Provides
  fun provideWeatherRepository(context: WeatherApp, apiInterface: WeatherApiInterface): WeatherRepository {
        return WeatherRepositoryImpl(context, apiInterface)
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): WeatherApp {
        return app as WeatherApp
    }
}








//@Provides
//@Singleton
//fun provideRetrofit(baseUrl: String): Retrofit {
// return Retrofit.Builder()
//   .baseUrl(baseUrl)
//   .addConverterFactory(GsonConverterFactory.create())
//    .build()
//}

//@Provides
//@Singleton
//fun provideWeatherApiInterface(retrofit: Retrofit): WeatherApiInterface {
// return retrofit.create(WeatherApiInterface::class.java)
//}

