package com.example.starbucksapp2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.starbucksapp2.model.StoreModel
import com.example.starbucksapp2.R
import com.example.starbucksapp2.databinding.ActivityMapViewBinding
import com.example.starbucksapp2.factory.MyViewModelFactory
import com.example.starbucksapp2.viewModel.MapViewModel

class MapViewActivity : AppCompatActivity() {
    lateinit var data: StoreModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMapViewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_map_view)
        intent.extras?.let { bundle ->
            data = (bundle.getParcelable("data") as? StoreModel)!!
        }
        val viewModel = ViewModelProvider(this, MyViewModelFactory(data))[MapViewModel::class.java]

        binding.mapViewModel = viewModel
        binding.lifecycleOwner = this

    }
}