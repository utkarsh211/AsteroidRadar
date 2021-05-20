package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.network.API_KEY
import com.udacity.asteroidradar.network.AsteroidRadarApi
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> get() = _pictureOfDay
    private val mainRepository = MainRepository(database)

    init {
        getPictureOfDay()
//        viewModelScope.launch {
//            mainRepository.refreshAsteroids()
//        }
    }
    private fun getPictureOfDay() {
        viewModelScope.launch {
            val pictureOfDay = AsteroidRadarApi.retrofitService.getImageOfTheDay(API_KEY)
            _pictureOfDay.value = pictureOfDay
        }
    }
//    val asteroidsList = mainRepository.asteroids
}