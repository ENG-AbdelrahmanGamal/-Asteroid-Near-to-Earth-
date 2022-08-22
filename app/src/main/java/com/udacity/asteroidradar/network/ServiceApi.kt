package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface AsteroidServiceAPI {
    @GET("rest/v1/feed")
   suspend fun getAster(
        @Query("api_key") key: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate:String
    ):String

   @GET("planetary/apod")
suspend fun getPicture(@Query("api_key")key:String): PictureOfDay

}
//mosh object

object Network {
    // Configure retrofit to parse JSON and use coroutines
    val retrofitService: AsteroidServiceAPI by lazy {
        retrofit.create(AsteroidServiceAPI::class.java)
    }

}