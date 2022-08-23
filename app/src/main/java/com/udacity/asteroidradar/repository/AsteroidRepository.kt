package com.udacity.asteroidradar.repository

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.RoomDatabase
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
class AsteroidRepository (private val roomDatabase:AsteroidDatabase) {
//   val aster: LiveData<List<Asteroid>> = Transformations.map(roomDatabase.asteroidDao.getAllAsteroid()) {
//        it.asDomainModel()
//    }


    val aster: LiveData<List<Asteroid>> =roomDatabase.asteroidDao.getAllAsteroid()
   suspend fun refreshAsteroid() {
            withContext(Dispatchers.IO) {
                val asList = parseAsteroidsJsonResult(
                    JSONObject( Network.retrofitService .getAster(Constants.MY_KEY,
                    getNextSevenDaysFormattedDates().get(0), getNextSevenDaysFormattedDates().get(6)).await()))
                roomDatabase.asteroidDao.insert(*asList.toTypedArray() )
        }
   }

}

