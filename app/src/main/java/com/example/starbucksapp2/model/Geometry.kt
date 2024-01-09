package com.example.starbucksapp2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Geometry(): Parcelable {
    @SerializedName("location")
    @Expose
    var location: Location? = null

    constructor(parcel: Parcel) : this() {
        location = parcel.readValue(Location::class.java.classLoader) as Location
    }

    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeValue(location)
    }

    companion object CREATOR : Parcelable.Creator<Geometry> {
        override fun createFromParcel(parcel: Parcel): Geometry {
            return Geometry(parcel)
        }

        override fun newArray(size: Int): Array<Geometry?> {
            return arrayOfNulls(size)
        }
    }
}