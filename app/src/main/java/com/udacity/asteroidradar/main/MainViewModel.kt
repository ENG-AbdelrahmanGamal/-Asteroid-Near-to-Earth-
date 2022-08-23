package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.Pic
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(val database: AsteroidDao, application: Application) :
    AndroidViewModel(application) {
    private val TAG = "MainViewModel"

    private  val firstDayOfWeek: String=getNextSevenDaysFormattedDates().get(0)
    private val  lastDayOfWeek: String=getNextSevenDaysFormattedDates().get(6)

    // create internal mutable live data
    private val _response = MutableLiveData<List<Asteroid>>()

    // create external live data can be access
    val response: LiveData<List<Asteroid>> get() = _response


    private val _navigate = MutableLiveData<Long?>()

    // create external live data can be access
    val navigate: MutableLiveData<Long?> get() = _navigate

    private val _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay> get() = _image

private val viewModelJop= Job()
    private val viewModelScope= CoroutineScope(viewModelJop+Dispatchers.IO)
    private val dp=getInstance(application)
    private val repository = AsteroidRepository(dp)


    init {
//        getAsteroidProperty()
     //  refreshAsteroid()
        viewModelScope.launch {
            repository.refreshAsteroid()
            Log.d(TAG, ": ${response.value}")
        }
      imageOfDay()
    }

    val astroidList=repository.aster

//    private fun refreshAsteroid()=viewModelScope.launch {
//        val asteroid=Network.retrofitService.getAster(Constants.MY_KEY,firstDayOfWeek,lastDayOfWeek).await()
//        _response.postValue(asteroid)
//
//    }

    fun asteroidOnClick(id: Long) {
        _navigate.value = id
    }

    fun asteroidOnClickNavigate() {
        _navigate.value = null
    }




//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun getAsteroidProperty() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO)
//            {
//                try {
//                    _response.postValue(getAsteroidPropertfromNetwork())
//                    Log.d(TAG, "getAsteroidProperty: done ${response.value}")
//                } catch (e: Exception) {
//                    Log.i(TAG, "getAsteroidProperty: failure ${e.localizedMessage} ")
//                }
//            }
//        }
//
//    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    private suspend fun getAsteroidPropertfromNetwork(): String? {
//        val deferredValue =
//            Network.retrofitService.getAster(Constants.MY_KEY, getStartDateTime(), lastDayOfWeek)
//        val result = deferredValue.await()
//        return result
//    }

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
//
//
//
    private suspend fun getImage(): PictureOfDay? {
            val deferredValue = Pic.retrofit.getPicture(Constants.MY_KEY)
            val result = deferredValue.await()
    Log.d(TAG, "getImage: $result")

    return result

        }



    }


