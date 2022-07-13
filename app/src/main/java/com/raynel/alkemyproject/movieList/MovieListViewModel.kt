package com.raynel.challenge.ViewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.raynel.alkemyproject.Repository.MoviesPageSource
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.model.PageListMovies
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(private val movieService: IMoviesService) : ViewModel() {

    private val _randomMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    fun randomMovie(): LiveData<List<Movie>> = _randomMovie

    private val _stateNavigateToDetailMovie: MutableLiveData<Movie> = MutableLiveData()
    fun stateNavigateToDetailMovie(): LiveData<Movie> = _stateNavigateToDetailMovie

    val flow = Pager(PagingConfig(1000)){
        MoviesPageSource(movieService)
    }.flow.cachedIn(viewModelScope)

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            try{
                val movies = movieService.getPopularMovies(5)
                _randomMovie.value = movies?.results?.subList(0, 3)?: emptyList()
            }catch (e: IOException){

            }
        }
    }

    fun navigateToMovieDatail(movie: Movie){
        _stateNavigateToDetailMovie.value = movie
    }

    fun doneNavigateToMovieDatail(){
        _stateNavigateToDetailMovie.value = null
    }

}

class MovieViewModelFactory(private val movieService: IMoviesService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(movieService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

