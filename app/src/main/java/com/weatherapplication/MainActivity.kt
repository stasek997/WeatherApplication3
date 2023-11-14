package com.weatherapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
    private var isReadyLongOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addAppReadyListener()
    }


    private fun addAppReadyListener() {
        Handler.createAsync(Looper.getMainLooper()).postDelayed(
            {
                isReadyLongOperation = true
            },
            5_000 // симуляция тяжёлой работы в 5 секунд
        )


        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {

                    return isReadyLongOperation.also {
                        if (isReadyLongOperation) content.viewTreeObserver.removeOnPreDrawListener(this)
                    }
                }
            }
        )
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

















































