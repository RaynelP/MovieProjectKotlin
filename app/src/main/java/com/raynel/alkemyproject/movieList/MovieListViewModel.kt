package com.raynel.challenge.ViewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.raynel.alkemyproject.GenericViewModel
import com.raynel.alkemyproject.Repository.MoviesPageSource
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(private val movieService: IMoviesService) : GenericViewModel() {

    private val _stateNavigateToDetailMovie: MutableLiveData<Movie> = MutableLiveData()
    fun stateNavigateToDetailMovie(): LiveData<Movie> = _stateNavigateToDetailMovie

    val flow = Pager(PagingConfig(1000)) {
        MoviesPageSource(movieService)
    }.flow.cachedIn(viewModelScope)

    fun navigateToMovieDatail(movie: Movie) {
        _stateNavigateToDetailMovie.value = movie
    }

    fun doneNavigateToMovieDatail() {
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

