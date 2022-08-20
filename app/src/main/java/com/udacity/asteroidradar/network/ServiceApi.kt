package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.Constants
import kotlinx.coroutines.Deferred
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()


interface AsteroidServiceAPI{
    @GET("apod")
    fun getAster(@Query("api_key") key:String): List<Asteroid>
    //suspend fun  getAsteroid(@Query("api_key") api_key: String): Asteroid

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
object Network {
    // Configure retrofit to parse JSON and use coroutines
    val retrofitService:AsteroidServiceAPI by lazy {
        retrofit.create(AsteroidServiceAPI::class.java)
    }

}