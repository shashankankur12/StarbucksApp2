package com.example.starbucksapp2.binding

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.starbucksapp2.base.BaseAdapter
import com.example.starbucksapp2.base.ListAdapterItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@BindingAdapter("setAdapter")
fun setAdapter(
    recyclerView: RecyclerView,
    adapter: BaseAdapter<ViewDataBinding, ListAdapterItem>?
) {
    adapter?.let {
        recyclerView.adapter = it
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<ListAdapterItem>?) {
    val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, ListAdapterItem>?
    adapter?.updateData(list ?: listOf())
}

@BindingAdapter("initMap")
fun initMap(mapView: MapView?, locations: LatLng?) {
    if (mapView != null) {
        mapView.onCreate(Bundle())
        mapView.getMapAsync(OnMapReadyCallback { googleMap: GoogleMap ->
            if (locations != null) {
                mapView.onResume()
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations, 13f))
                googleMap.addMarker(
                    MarkerOptions().position(
                        locations
                    )
                )
            }
        })
    }
}