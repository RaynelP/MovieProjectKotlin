package com.raynel.challenge.ViewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.raynel.alkemyproject.Repository.MoviesPageSource
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(private val movieService: IMoviesService) : ViewModel() {

    private val _randomMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    fun randomMovie(): LiveData<List<Movie>> = _randomMovie

    private val _stateNavigateToDetailMovie: MutableLiveData<Movie> = MutableLiveData()
    fun stateNavigateToDetailMovie(): LiveData<Movie> = _stateNavigateToDetailMovie

    private val _retry: MutableLiveData<Boolean> = MutableLiveData()
    fun retry(): LiveData<Boolean> = _retry

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    fun error(): LiveData<Boolean> = _error

    val flow = Pager(PagingConfig(1000)){
        MoviesPageSource(movieService)
    }.flow.cachedIn(viewModelScope)

    init {
        getMoviesRandom()
    }

    fun getMoviesRandom() {
        viewModelScope.launch {
            try{
                val movies = movieService.getPopularMovies(5)
                _randomMovie.value = movies?.results?.subList(0, 3)?: emptyList()
            }catch (e: IOException){
                showError()
            }
        }
    }

    fun navigateToMovieDatail(movie: Movie){
        _stateNavigateToDetailMovie.value = movie
    }

    fun doneNavigateToMovieDatail(){
        _stateNavigateToDetailMovie.value = null
    }

    fun startRetry(){
        _retry.value = true
    }

    fun doneRetry(){
        _retry.value = null
    }

    fun showError(){
        _error.value = true
    }

    fun doneShowError(){
        _error.value = null
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

