package com.weatherapplication.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf

import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.bumptech.glide.Glide
import com.example.weatherapplication.R

import com.example.weatherapplication.databinding.WeatherFragmentBinding

import com.weatherapplication.viewmodels.MainActivityViewModel



import com.weatherapplication.data.WeatherData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment () : Fragment() {

    companion object {
        const val KEY_CITY_ID = "key_city_id"
        const val KEY_BUNDLE_ID = "key_bundle_id"
    }

    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<MainActivityViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListen()
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_settingsScreenFragment)
        }
        setLiveDataListeners()
        setCash()

    }

    private fun setCash() {
        AppCompatDelegate.setDefaultNightMode(viewModel.dayNightMode)
    }

    private fun setListen() {
        parentFragmentManager.setFragmentResultListener(
            KEY_CITY_ID, this
        ) { _, bundle ->
           viewModel.getWeatherInfo(bundle.getInt(KEY_BUNDLE_ID))
        }

    }

    private fun setLiveDataListeners() {


        viewModel.progressBarLiveData.observe(viewLifecycleOwner, Observer { isShowLoader ->
            if (isShowLoader)
                binding.weatherProgressBar.visibility = View.VISIBLE
            else
                binding.weatherProgressBar.visibility = View.GONE
        })


        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })


        viewModel.weatherInfoFailureLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            binding.outputGroup.visibility = View.GONE
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = errorMessage
        })
    }


    private fun setWeatherInfo(weatherData: WeatherData) {
        with(binding) {
            outputGroup.visibility = View.VISIBLE
            tvErrorMessage.visibility = View.GONE

            tvDateTime.text = weatherData.dateTime
            tvTemperature.text = weatherData.temperature
            tvCityCountry.text = weatherData.cityAndCountry
            Glide.with(requireContext()).load(weatherData.weatherConditionIconUrl)
                .into(ivWeatherCondition)
            tvWeatherCondition.text = weatherData.weatherConditionIconDescription

            tvHumidityValue.text = weatherData.humidity
            tvPressureValue.text = weatherData.pressure
            tvVisibilityValue.text = weatherData.visibility

            tvSunriseTime.text = weatherData.sunrise
            tvSunsetTime.text = weatherData.sunset
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}