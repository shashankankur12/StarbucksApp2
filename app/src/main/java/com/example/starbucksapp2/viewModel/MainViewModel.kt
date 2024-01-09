package com.example.starbucksapp2.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starbucksapp2.comman.Resource
import com.example.starbucksapp2.model.Location
import com.example.starbucksapp2.model.ResponseModel
import com.example.starbucksapp2.model.StoreModel
import com.example.starbucksapp2.networking.DataManager
import com.example.starbucksapp2.networking.Repository
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.round
import kotlin.math.roundToLong


class MainViewModel : ViewModel() {
    var resultList: MutableLiveData<Resource<List<StoreModel>>> = MutableLiveData()
    var latLng: MutableLiveData<LatLng> = MutableLiveData()


    private var responseModelObserver: Observer<ResponseModel> = object : Observer<ResponseModel> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(res: ResponseModel) {
            if (res.status == "OK") {
                val updatedValue = updateLotLongForStores(res.results)
                resultList.postValue(Resource.success(updatedValue))
            } else {
                resultList.postValue(Resource.error(res.status))
                Log.d("Error", res.status)
            }
        }

        override fun onError(e: Throwable) {
            resultList.postValue(Resource.error(e.toString()))
            Log.d("Error", e.toString())
        }

        override fun onComplete() {
        }
    }

    fun getData() {
        resultList.postValue(Resource.loading())
        val repository = Repository()
        val networkCall = DataManager()

        repository.getApiObservable(networkCall, latLng.value).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(responseModelObserver)
    }

    fun updateLotLongForStores(results: List<StoreModel>): List<StoreModel> {
        results.forEach { it.distance = calculateDistance(it.geometry?.location) }
        return results.sortedBy { it.distance }
    }

    fun calculateDistance(location: Location?): Long {
        var distance = 0L
        location?.let {
            val startPoint = android.location.Location("locationA")
            startPoint.latitude = latLng.value!!.latitude
            startPoint.longitude = latLng.value!!.longitude

            val endPoint = android.location.Location("locationB")
            endPoint.latitude = it.latitude
            endPoint.longitude = it.longitude
            distance = round(startPoint.distanceTo(endPoint) / 1000).roundToLong()
        }

        return distance
    }
}