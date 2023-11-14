package com.weatherapplication.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<MainActivityViewModel>()

    companion object {
        const val KEY_CITY_ID = "key_city_id"
        const val KEY_BUNDLE_ID = "key_bundle_id"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_settingsScreenFragment)
        }
        setLiveDataListeners()
      //  setListen()
        //setupViews()
    }

    //private fun setupViews() {
    //  binding.weatherRecyclerView.layoutManager
    //}


    private fun setLiveDataListeners() {


        /**
         * ProgressBar visibility will be handled by this LiveData. ViewModel decides when Activity
         * should show ProgressBar and when hide.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.progressBarLiveData.observe(viewLifecycleOwner, Observer { isShowLoader ->
            if (isShowLoader)
                binding.weatherProgressBar.visibility = View.VISIBLE
            else
                binding.weatherProgressBar.visibility = View.GONE
        })

        /**
         * This method will be triggered when ViewModel successfully receive WeatherData from our
         * data source (I mean Model). Activity just observing (subscribing) this LiveData for showing
         * weather information on UI. ViewModel receives Weather data API response from Model via
         * Callback method of Model. Then ViewModel apply some business logic and manipulate data.
         * Finally ViewModel PUSH WeatherData to `weatherInfoLiveData`. After PUSHING into it, below
         * method triggered instantly! Then we set the data on UI.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })

        /**
         * If ViewModel faces any error during Weather Info fetching API call by Model, then PUSH the
         * error message into `weatherInfoFailureLiveData`. After that, this method will be triggered.
         * Then we will hide the output view and show error message on UI.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.weatherInfoFailureLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            binding.outputGroup.visibility = View.GONE
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = errorMessage
        })
    }
//    private fun setListen() {
//        parentFragmentManager.setFragmentResultListener(
//            KEY_CITY_ID, this
//        ) { _, bundle ->
//          viewModel.getWeatherInfo(
//             bundle.getInt(KEY_BUNDLE_ID)
//          )
//        }
//    }

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