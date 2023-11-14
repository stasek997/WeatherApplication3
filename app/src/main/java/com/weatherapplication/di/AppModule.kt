package com.weatherapplication.di

import android.content.Context
import com.weatherapplication.WeatherApp
import com.weatherapplication.network.WeatherApiInterface
import com.weatherapplication.repository.WeatherRepository
import com.weatherapplication.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
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
  fun provideWeatherRepository(
       context: WeatherApp, apiInterface: WeatherApiInterface
  ): WeatherRepository {
        return WeatherRepositoryImpl(
            context, apiInterface
        )
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

