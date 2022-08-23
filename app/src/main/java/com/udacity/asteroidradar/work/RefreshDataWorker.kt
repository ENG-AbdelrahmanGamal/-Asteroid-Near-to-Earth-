package com.udacity.asteroidradar.work

import android.bluetooth.BluetoothStatusCodes.SUCCESS
import android.content.Context
import android.content.ContextParams
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext:Context,params: WorkerParameters) :CoroutineWorker(appContext,params){
    private  val TAG = "RefreshDataWorker"
    @RequiresApi(Build.VERSION_CODES.N)

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroid()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}