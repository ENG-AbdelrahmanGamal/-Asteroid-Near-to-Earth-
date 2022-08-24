package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend  fun insert(asteroid: Array<Asteroid>)

    @Update
    suspend fun update(asteroid:Asteroid )

    @Query("DELETE FROM Asteroid")
  suspend   fun clear()

    @Query("SELECT * FROM Asteroid ")
    fun getAllAsteroid():LiveData<List<Asteroid>>


    @Query("SELECT * FROM Asteroid where closeApproachDate=:key")
    fun getTodayAsteroid(key:String):LiveData<List<Asteroid>>

}