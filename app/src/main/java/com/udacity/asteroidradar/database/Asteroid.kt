package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Asteroid(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable {
    fun forEachIndex(any: Any) {

    }
}


//fun List<AsteroidDatabase>.asDomainModel(): List<Asteroid> {
//    return map {
//        Asteroid(
//            codename = it.codename,
//            title = it.title,
//            description = it.description,
//            updated = it.updated,
//            thumbnail = it.thumbnail)
//    }
//}