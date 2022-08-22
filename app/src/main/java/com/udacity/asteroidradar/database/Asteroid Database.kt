package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asteroid::class], version = 1, exportSchema = false )
abstract class AsteroidDatabase  :RoomDatabase(){

    abstract val asteroidDao:AsteroidDao
    companion object{
        //this instance is always up to data and execution
        //well never be cached
        @Volatile
        private var INSTANCE:AsteroidDatabase?=null

        fun getInstance(context:Context):AsteroidDatabase{
            synchronized(this){
                var instant= INSTANCE
                if(instant==null){
                    instant= Room.databaseBuilder(
                        context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid Hestory")
                        .fallbackToDestructiveMigration().build()
                }
                return instant
            }
        }
    }
}