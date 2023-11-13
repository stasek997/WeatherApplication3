package com.weatherapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.AddScreenFragmentBinding

import com.hellohasan.weatherappmvvm.utils.convertToListOfCityName
import com.weatherapplication.data.City
import com.weatherapplication.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddScreenFragment : Fragment(){

    private var _binding: AddScreenFragmentBinding? = null
    private val binding get() = _binding!!


    private var cityList: MutableList<City> = mutableListOf()


    private val viewModel by viewModels<MainActivityViewModel>()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root}



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AcceptButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsScreenFragment_to_mainScreenFragment)}

        viewModel.getCityList()
        setViewClickListener()
        setLiveDataListeners()
    }

    private fun setLiveDataListeners() {


        viewModel.cityListLiveData.observe(viewLifecycleOwner, object :
            Observer<MutableList<City>> {
            override fun onChanged(cities: MutableList<City>) {
                setCityListSpinner(cities)
            }
        })


        viewModel.cityListFailureLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        })
    }

    private fun setCityListSpinner(cityList: MutableList<City>) {
        this.cityList = cityList

        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,
            this.cityList.convertToListOfCityName()
        )

        binding.spinner.adapter = arrayAdapter
    }


    private fun setViewClickListener() {
        // View Weather button click listener
        binding.btnViewWeather.setOnClickListener {
            val selectedCityId = cityList[binding.spinner.selectedItemPosition].id
            viewModel.getWeatherInfo(selectedCityId) // fetch weather info
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

