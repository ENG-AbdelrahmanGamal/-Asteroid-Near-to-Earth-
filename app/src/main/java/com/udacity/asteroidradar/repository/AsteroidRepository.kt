package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//class AsteroidRepository (private val asteroid: Asteroid) {
//    val aster: LiveData<List<Asteroid>> = Transformations.map(AsteroidDao.getAllAsteroid()) {
//        it.asDomainModel()
//    }
//    suspend fun refreshAsteroid() {
//            withContext(Dispatchers.IO) {
//                val asList = Network..getPlaylist().await()
//                database.videoDao.insertAll(*playlist.asDatabaseModel())
//
//        }
//    }
//    val playlist = Network.aster.().await()
//    database.videoDao.insertAll(*playlist.asDatabaseModel())
//
//
//}