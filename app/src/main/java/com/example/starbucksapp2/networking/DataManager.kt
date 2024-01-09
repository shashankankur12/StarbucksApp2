package com.example.starbucksapp2.networking

import android.util.Log
import androidx.core.os.BuildCompat
import com.example.starbucksapp2.BuildConfig
import com.google.android.gms.maps.model.LatLng
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class DataManager {

    private var responseCode = 0

    fun makeGetServiceCall(latLng: LatLng): String? {
        var connection: HttpURLConnection? = null
        var response: String? = null
        var inputStream: InputStream? = null
        val requestUrl= BuildConfig.BASE_API.plus("&location=${latLng.latitude},${latLng.longitude}")
        Log.d("requestUrl", requestUrl)
        try {
            val imgUrl = URL(requestUrl)
            connection = imgUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            responseCode = connection.responseCode
            inputStream = connection.inputStream
            response = readIt(inputStream)
            Log.d("response", response!!)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) {
                    connection!!.disconnect()
                    inputStream.close()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return response
    }

    @Throws(java.lang.Exception::class)
    private fun readIt(iStream: InputStream?): String? {
        var singleLine: String? = ""
        val totalLines = StringBuilder(iStream!!.available())
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(iStream))
            while (reader.readLine().also { singleLine = it } != null) {
                totalLines.append(singleLine)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return totalLines.toString()
    }
}
