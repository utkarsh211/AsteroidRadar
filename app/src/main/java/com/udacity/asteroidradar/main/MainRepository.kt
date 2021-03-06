package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val database: AsteroidsDatabase) {
    private val todayDate: String = getNextSevenDaysFormattedDates()[0]

    fun getAsteroidsList(filterValue: String): LiveData<List<Asteroid>> {
        return when (filterValue) {
            "all" -> Transformations.map(database.asteroidDao.getAsteroids()) {
                it.asDomainModel()
            }
            "today" -> Transformations.map(database.asteroidDao.getTodayAsteroids(todayDate)) {
                it.asDomainModel()
            }
            else -> Transformations.map(database.asteroidDao.getAsteroids()) {
                it.asDomainModel()
            }
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

            val asteroidResponse = AsteroidRadarApi.retrofitService.getAsteroids( getNextSevenDaysFormattedDates()[0], getNextSevenDaysFormattedDates()[Constants.DEFAULT_END_DATE_DAYS],
                API_KEY).await()
            val asteroidsList = parseAsteroidsJsonResult(JSONObject( asteroidResponse.string()))
            database.asteroidDao.insertAll(*NetworkAsteroidContainer(asteroidsList).asDatabaseModel())

        }
    }
    suspend fun getPictureOfDay(): PictureOfDay{
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO){
            pictureOfDay = AsteroidRadarApi.retrofitService.getImageOfTheDay(API_KEY)
        }
        return pictureOfDay
    }
}