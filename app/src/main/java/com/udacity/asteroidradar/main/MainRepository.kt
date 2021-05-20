package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: AsteroidsDatabase) {

//    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()){
//        it.asDomainModel()
//    }
//    suspend fun refreshAsteroids() {
//        withContext(Dispatchers.IO) {
//            val asteroidsList = parseAsteroidsJsonResult(AsteroidRadarApi.retrofitService.getAsteroids(
//                getNextSevenDaysFormattedDates()[0], getNextSevenDaysFormattedDates()[Constants.DEFAULT_END_DATE_DAYS],
//                API_KEY))
//            database.asteroidDao.insertAll(* NetworkAsteroidContainer(asteroidsList).asDatabaseModel())
//        }
//    }
}