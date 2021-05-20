package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.domain.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "hEiiLwA47o2AQXIS0KmAyyltjFr1yHtShcW9rGbP"
interface  AsteroidRadarService {
    @GET("planetary/apod")
    suspend fun getImageOfTheDay(@Query("api_key")apiKey: String): PictureOfDay

    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date")startDate: String, @Query("end_date")
                endDate: String, @Query("api_key") apiKey: String): JSONObject
}
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

object AsteroidRadarApi {
    val retrofitService: AsteroidRadarService by lazy {
        retrofit.create(AsteroidRadarService::class.java)
    }
}