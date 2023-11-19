package com.weatherapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.AddScreenFragmentBinding

import com.hellohasan.weatherappmvvm.utils.convertToListOfCityName
import com.weatherapplication.data.City
import com.weatherapplication.repository.WeatherPref
import com.weatherapplication.viewmodels.AddScreenViewModel
import com.weatherapplication.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddScreenFragment : Fragment() {

    private var _binding: AddScreenFragmentBinding? = null
    private val binding get() = _binding!!


    private var cityList: MutableList<City> = mutableListOf()


    private val viewModel by viewModels<AddScreenViewModel>()

    // @Inject
    //  lateinit var pref: WeatherPref


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.cityId = cityList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        binding.AcceptButton.setOnClickListener {

            val selectedCityId = cityList[binding.spinner.selectedItemPosition].id
            setFragmentResult(
                WeatherFragment.KEY_CITY_ID,
                bundleOf(
                    WeatherFragment.KEY_BUNDLE_ID to selectedCityId
                )
            )
            findNavController().navigate(R.id.action_settingsScreenFragment_to_mainScreenFragment)
        }

        binding.changeTheme.setOnCheckedChangeListener { _, checked ->
            val mode = when (checked) {
                true -> AppCompatDelegate.MODE_NIGHT_YES

                false -> AppCompatDelegate.MODE_NIGHT_NO
            }
            viewModel.dayNightMode = mode
            Log.i("AddScreen", "mode $mode")

            AppCompatDelegate.setDefaultNightMode(mode)

        }
        val isChecked = viewModel.dayNightMode == AppCompatDelegate.MODE_NIGHT_YES
        binding.changeTheme.isChecked = isChecked
        Log.i("AddScreen", "isChecked ${viewModel.dayNightMode} $isChecked")
        viewModel.getCityList()
        // setViewClickListener()
        setLiveDataListeners()
    }

    private fun setLiveDataListeners() {


        viewModel.cityListLiveData.observe(
            viewLifecycleOwner
        ) { cities -> setCityListSpinner(cities) }


        viewModel.cityListFailureLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        })
    }

    private fun setCityListSpinner(cityList: MutableList<City>) {
        this.cityList = cityList

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            this.cityList.convertToListOfCityName()
        )

        binding.spinner.adapter = arrayAdapter
        val cashed = viewModel.cityId
        val cashedCity = cityList.firstOrNull { it == cashed }
        if (cashedCity != null) {
            binding.spinner.setSelection(
                cityList.indexOf(cashedCity)
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

