package com.example.starbucksapp2.networking

import com.example.starbucksapp2.model.ResponseModel
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable

class Repository {
    internal fun getApiObservable(
        networkCall: DataManager,
        latLng: LatLng?
    ): Observable<ResponseModel> {
        return Observable.create { emitter ->
            try {
                val response: String =
                    networkCall.makeGetServiceCall(latLng!!)!!
                val res = Gson().fromJson(response, ResponseModel::class.java)
                emitter.onNext(res)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }


}
