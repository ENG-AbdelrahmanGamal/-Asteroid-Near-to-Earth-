package com.udacity.asteroidradar.main

import android.app.Application
import android.icu.text.DateFormat
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.network.AsteroidServiceAPI
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.*
import java.lang.Exception
import java.time.DayOfWeek

class MainViewModel (val database: AsteroidDao, application : Application):AndroidViewModel(application) {
    private  val TAG = "MainViewModel"
    // create internal mutable live data
    private val _response = MutableLiveData<String>()

    // create external live data can be access
    val response: LiveData<String> get() = _response
    private val _navigate = MutableLiveData<Long?>()

    // create external live data can be access
    val navigate: MutableLiveData<Long?> get() = _navigate

    fun asteroidOnClick(id:Long) {
        _navigate.value=id
    }
    fun asteroidOnClickNavigate(){
        _navigate.value=null
    }


    init {
        getAsteroidProperty()
    }

    private fun getAsteroidProperty() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                try {
                    _response.value=getAsteroidPropertfromNetwork()
                    Log.d(TAG, "getAsteroidProperty: ")
                }
                catch (e:Exception)
                {
                    Log.i(TAG, "getAsteroidProperty:$e ")
                }
            }
        }

    }
private val today:String=DateFormat.DAY
    private val endTime :String=today+7
    private suspend fun getAsteroidPropertfromNetwork(): String? {
       return Network.retrofitService.getAster("XPRdzkcr3buvCIiFJe39ZZzsg2empbt154hMbrgx",today,endTime)
       }

//private suspend fun insert(asteroid: Asteroid)
//{
//    withContext(Dispatchers.IO)
//    {
//        database.insert(asteroid)
//    }
//}

}