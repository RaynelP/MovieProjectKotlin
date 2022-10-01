package com.raynel.alkemyproject.view.principalActivity.home

import androidx.lifecycle.*
import com.raynel.alkemyproject.util.GenericViewModel
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val movieService: IMoviesService): GenericViewModel() {

    private val _randomMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    fun randomMovie(): LiveData<List<Movie>> = _randomMovie

    init {
        onLoanding()
        getMoviesRandom()
    }

    private fun getMoviesRandom() {
        viewModelScope.launch {
            try {
                val movies = movieService.getPopularMovies(5)
                _randomMovie.value = movies?.results
                if(movies?.results == null){
                    onError()
                }
            } catch (e: IOException) {
                onError()
            }
            doneLoanding()
        }
    }
}

class HomeViewModelFactory(private val movieService: IMoviesService)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(movieService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}