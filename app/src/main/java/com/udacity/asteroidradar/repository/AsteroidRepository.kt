package com.udacity.asteroidradar.repository

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.BuildConfig.NASA_API_KEY
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.AsteroidServiceAPI
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*


// repository for fetch asteroid from network
@RequiresApi(Build.VERSION_CODES.N)
class AsteroidRepository(private val roomDatabase: AsteroidDatabase) {


    val asteroids: LiveData<List<Asteroid>> = roomDatabase.asteroidDao.getAllAsteroid()
    val todayAsteroid: LiveData<List<Asteroid>> = roomDatabase.asteroidDao.getTodayAsteroid(
        getNextSevenDaysFormattedDates().get(0)
    )

    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            val asList = parseAsteroidsJsonResult(
                JSONObject(
                    Network.retrofitService.getAster(
                        NASA_API_KEY,
                        getNextSevenDaysFormattedDates().get(0),
                        getNextSevenDaysFormattedDates().get(6)
                    ).await()
                )
            )
            roomDatabase.asteroidDao.insert(*asList.toTypedArray())
        }
    }


}

