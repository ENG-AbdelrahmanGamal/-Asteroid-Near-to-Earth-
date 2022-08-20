package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.network.AsteroidServiceAPI
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel : ViewModel() {
    private  val TAG = "MainViewModel"
    // create internal mutable live data
    private val _response = MutableLiveData<List<Asteroid>>()

    // create external live data can be access
    val response: LiveData<List<Asteroid>> get() = _response

    init {
        getAsteroidProperty()
    }

    private fun getAsteroidProperty() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                try {
                    _response.value= Network.retrofitService.getAster("XPRdzkcr3buvCIiFJe39ZZzsg2empbt154hMbrgx")
                    Log.d(TAG, "getAsteroidProperty: ")
                }
                catch (e:Exception)
                {
                    Log.i(TAG, "getAsteroidProperty:$e ")
                }
            }
        }

    }
}