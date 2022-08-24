package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.network.Pic
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(val database: AsteroidDao, application: Application) :
    AndroidViewModel(application) {
    private val TAG = "MainViewModel"


    private val _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay> get() = _image

    private val viewModelJop = Job()
    private val viewModelScope = CoroutineScope(viewModelJop + Dispatchers.IO)
    private val dp = getInstance(application)
    private val repository = AsteroidRepository(dp)
    val astroidList = repository.asteroids
    val todayAsteroid = repository.todayAsteroid


    init {
        viewModelScope.launch {
            repository.refreshAsteroid()
        }
        imageOfDay()
    }


    private fun imageOfDay() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                _image.postValue(getImage())
                Log.d(TAG, "imageOfDay: Done")

            }
            try {
            } catch (e: Exception) {
                Log.d(TAG, "imageOfDay: failure ${e.localizedMessage}")
            }
        }
    }

    private suspend fun getImage(): PictureOfDay? {
        val deferredValue = Pic.retrofit.getPicture(Constants.MY_KEY)
        val result = deferredValue.await()
        Log.d(TAG, "getImage: $result")
        return result

    }


}


