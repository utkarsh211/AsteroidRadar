package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidApiFilter
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> get() = _pictureOfDay
    private val mainRepository = MainRepository(database)
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid> get() = _navigateToSelectedAsteroid
    private val periodOfAsteroidsData = MutableLiveData(AsteroidApiFilter.SHOW_ALL)

    var asteroidsList: LiveData<List<Asteroid>> = Transformations.switchMap(periodOfAsteroidsData) {
        mainRepository.getAsteroidsList(it.value)
    }


    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }
    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }
//    fun updateFilter(filterValue: String){
//       getAsteroidsList(filterValue)
//    }
    fun updateFilter(filter: AsteroidApiFilter){
        getAsteroidsList(filter)
    }
//    private fun getAsteroidsList(filterValue: String){
//        asteroidsList = mainRepository.getAsteroidsList(filterValue)
//    }
    private fun getAsteroidsList (filter: AsteroidApiFilter) {
        periodOfAsteroidsData.value = filter
    }


    init {
        viewModelScope.launch {
            _pictureOfDay.value = mainRepository.getPictureOfDay()
            mainRepository.refreshAsteroids()
        }
//        getAsteroidsList(AsteroidApiFilter.SHOW_ALL.value)
        getAsteroidsList(AsteroidApiFilter.SHOW_ALL)
    }

}