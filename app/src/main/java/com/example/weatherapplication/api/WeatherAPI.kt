package com.example.weatherapplication.api

import com.example.weatherapplication.entity.LocationData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        const val API_KEY = "10cdb27e429141cd9f5170103250304"
    }

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") apiKey: String = API_KEY,
        @Query("q") location: String
    ): Response<List<LocationData>>
}