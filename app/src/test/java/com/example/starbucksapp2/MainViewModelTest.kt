package com.example.starbucksapp2

import com.example.starbucksapp2.model.Location
import com.example.starbucksapp2.viewModel.MainViewModel
import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private val mainViewModel: MainViewModel? = null

    @Test
    fun testCalculateDistance() {
        val location = Location()
        location.latitude = 37.339446
        location.longitude = -121.842163
        mainViewModel?.latLng?.value = LatLng(37.421998333333335, -122.08400000000002)

        mainViewModel?.calculateDistance(location)
    }
}