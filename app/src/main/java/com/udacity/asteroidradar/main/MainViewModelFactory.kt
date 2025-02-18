package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidDao

class MainViewModelFactory(
    private val dataSource:AsteroidDao  ,
    private val application: Application
):ViewModelProvider.Factory
{
    @RequiresApi(Build.VERSION_CODES.N)
    @Suppress("uncheck")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
if (modelClass.isAssignableFrom(MainViewModel::class.java))
    return (MainViewModel(dataSource,application)as T)
        else
            throw IllegalArgumentException("Unknown ViewModel Class")
    }

}