package com.example.starbucksapp2.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.starbucksapp2.R
import com.example.starbucksapp2.adaptor.RestaurantAdaptor
import com.example.starbucksapp2.adaptor.RestaurantListener
import com.example.starbucksapp2.comman.GpsTracker
import com.example.starbucksapp2.databinding.ActivityMainBinding
import com.example.starbucksapp2.model.StoreModel
import com.example.starbucksapp2.viewModel.MainViewModel
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), RestaurantListener {
    private lateinit var gpsTracker: GpsTracker
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        checkPermission()
        localCurrentLocation()
        binding.viewModel = viewModel
        binding.adapter = RestaurantAdaptor(listOf(), this)
        binding.lifecycleOwner = this
    }

    private fun localCurrentLocation() {
        gpsTracker = GpsTracker(this@MainActivity)
        if (gpsTracker.canGetLocation()) {
            val latitude = gpsTracker.getLatitude()
            val longitude = gpsTracker.getLongitude()
            val latLng = LatLng(latitude, longitude)
            viewModel.latLng.value = latLng
            viewModel.getData()
        } else {
            gpsTracker.showSettingsAlert()
        }
    }

    private fun checkPermission() {
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    localCurrentLocation()
                } else {

                }
            }
        }
    }

    override fun onRestaurantClicked(restaurant: StoreModel) {
        val intent = Intent(this, MapViewActivity::class.java)
        intent.putExtra("data", restaurant)
        startActivity(intent)
    }
}