package com.example.starbucksapp2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starbucksapp2.model.StoreModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel(model: StoreModel): ViewModel() {
    val latLong : MutableLiveData<LatLng> = MutableLiveData()
    val places : MutableLiveData<String> = MutableLiveData()

    init {
        latLong.value= LatLng(model.geometry!!.location!!.latitude, model.geometry!!.location!!.longitude)
        places.value= model.getCompleteAddress()
    }
}