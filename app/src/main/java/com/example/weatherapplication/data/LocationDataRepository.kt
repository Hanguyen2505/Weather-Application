package com.example.weatherapplication.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.example.weatherapplication.api.RetrofitInstance
import com.example.weatherapplication.api.WeatherAPI
import com.example.weatherapplication.entity.LocationData
import com.example.weatherapplication.entity.WeatherData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.Locale

class LocationDataRepository {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onSuccess: (latitude: Double, longitude: Double) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            onSuccess(
                location.latitude,
                location.longitude
            )
            Log.d("WeatherDataRepository", "Current location: $location")
        }.addOnFailureListener { exception ->
            onFailure(exception)
            Log.e("WeatherDataRepository", "Error getting current location", exception)
        }
    }

    suspend fun searchLocation(query: String): List<LocationData> {
        val response = RetrofitInstance.api.searchLocation(location = query)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }
}