package com.udacity.asteroidradar.main

import android.app.Application
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.text.format.DateFormat.format
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.Pic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.http.HttpDate.format
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext.plus

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(val database: AsteroidDao, application: Application) :
    AndroidViewModel(application) {
    private val TAG = "MainViewModel"

    private lateinit var firstDayOfWeek: String
    private lateinit var lastDayOfWeek: String

    // create internal mutable live data
    private val _response = MutableLiveData<String>()

    // create external live data can be access
    val response: LiveData<String> get() = _response
    private val _navigate = MutableLiveData<Long?>()

    // create external live data can be access
    val navigate: MutableLiveData<Long?> get() = _navigate

    private val _imgeURL = MutableLiveData<PictureOfDay>()
    val imageURL: LiveData<PictureOfDay> get() = _imgeURL


    init {
        getAsteroidProperty()
        Log.d(TAG, "${getStartDateTime()}: ")
        Log.d(TAG, "${getEndDateTime()}: ")
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val format = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        firstDayOfWeek = format.format(currentTime)
        calendar.add(Calendar.DAY_OF_YEAR,Constants.DEFAULT_END_DATE_DAYS)
        val endWeek = calendar.time
        lastDayOfWeek = format.format(endWeek)
        Log.d(TAG, "date is :$firstDayOfWeek ")
        Log.d(TAG, "lastDayOfWeek:$lastDayOfWeek ")
      //  imageOfDay()
    }

    fun asteroidOnClick(id: Long) {
        _navigate.value = id
    }

    fun asteroidOnClickNavigate() {
        _navigate.value = null
    }




    @RequiresApi(Build.VERSION_CODES.N)
    private fun getAsteroidProperty() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                try {
                    _response.postValue(getAsteroidPropertfromNetwork())
                    Log.d(TAG, "getAsteroidProperty: done ${response.value}")
                } catch (e: Exception) {
                    Log.i(TAG, "getAsteroidProperty: failure ${e.localizedMessage} ")
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun getAsteroidPropertfromNetwork(): String? {
        val deferredValue =
            Network.retrofitService.getAster(Constants.MY_KEY, getStartDateTime(), lastDayOfWeek)
        val result = deferredValue.await()
        return result
    }

//    private fun imageOfDay() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO)
//            {
//                _imgeURL.value=getImage()
//                Log.d(TAG, "imageOfDay: Done")
//
//            }
//                try {
//                } catch (e: Exception) {
//                    Log.d(TAG, "imageOfDay: failure ${e.localizedMessage}")
//                }
//            }
//        }
//
//
//
//    private suspend fun getImage(): PictureOfDay? {
//            val deferredValue = Pic.retrofit.getPicture(Constants.DAY_IMAGE)
//            val result = deferredValue.await()
//            return result
//        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun getStartDateTime(): String {
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, -2)
            return dateFormat.format(calendar.time)
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun getEndDateTime(): String {
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)


        }

    }


