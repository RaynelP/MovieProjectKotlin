package com.raynel.alkemyproject.view.mainActivity.home

import androidx.lifecycle.*
import com.raynel.alkemyproject.util.GenericViewModel
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieService: IMoviesService
) : GenericViewModel() {

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