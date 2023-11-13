package com.weatherapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import com.weatherapplication.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}




      //  private lateinit var binding: ActivityMainBinding
      //  override fun onCreate(savedInstanceState: Bundle?) {
        //    super.onCreate(savedInstanceState)

          //  binding = ActivityMainBinding.inflate(layoutInflater)
           // setContentView(binding.root)
       // }
   // }
//private fun setupObservers() {
  //  viewModel.getAll()

    //viewModel.databaseData.observe(this) { newData ->
      //  mainRecyclerViewAdapter?.differ?.submitList(newData)
    //}
//}

//private fun setupViews() {
  //  mainRecyclerViewAdapter = ItemsListAdapter(
    //    context = requireContext(),
      //  increaseItemAmountInStorage = { item ->
        //    viewModel.updateItem(item)
        //},
        //decreaseItemAmountInStorage = { item ->
           // viewModel.updateItem(item)
        //},
        //canButtonClick = { item ->
          //  viewModel.deleteItem(item)
        //},
        //onUpdateItemClick = { shoppingItem ->
          //  viewModel.setActiveId(shoppingItem.id)
           // findNavController().navigate(R.id.action_mainScreenFragment_to_addItemFragment)
        //}
   // )

    //binding.recyclerView.adapter = mainRecyclerViewAdapter
    //binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    //binding.addItemButton.setOnClickListener {
      //  findNavController().navigate(R.id.action_mainScreenFragment_to_addItemFragment)
    //}

















































