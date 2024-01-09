package com.example.starbucksapp2.model

import android.os.Parcel
import android.os.Parcelable
import com.example.starbucksapp2.base.ListAdapterItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StoreModel() : ListAdapterItem, Parcelable {
    @SerializedName("place_id")
    @Expose
    override var id: String? = null

    @SerializedName("distance")
    @Expose
    var distance: Long = 0L

    @SerializedName("vicinity")
    @Expose
    var addressLines: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        distance = parcel.readLong()
        addressLines = parcel.readString()
        name = parcel.readString()
        geometry = parcel.readValue(Geometry::class.java.classLoader) as Geometry
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getCompleteAddress(): String {
        return "$name, $addressLines"
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(id)
        parcel.writeLong(distance)
        parcel.writeString(addressLines)
        parcel.writeString(name)
        parcel.writeValue(geometry)
    }

    companion object CREATOR : Parcelable.Creator<StoreModel> {
        override fun createFromParcel(parcel: Parcel): StoreModel {
            return StoreModel(parcel)
        }

        override fun newArray(size: Int): Array<StoreModel?> {
            return arrayOfNulls(size)
        }
    }
}