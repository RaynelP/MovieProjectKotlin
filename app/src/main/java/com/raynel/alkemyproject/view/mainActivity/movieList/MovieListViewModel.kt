package com.raynel.challenge.ViewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.raynel.alkemyproject.util.GenericViewModel
import com.raynel.alkemyproject.Repository.MoviesPageSource
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieService: IMoviesService
) : GenericViewModel() {

    private val _stateNavigateToDetailMovie: MutableLiveData<Movie?> = MutableLiveData()
    fun stateNavigateToDetailMovie(): LiveData<Movie?> = _stateNavigateToDetailMovie

    val flow = Pager(PagingConfig(1000)) {
        MoviesPageSource(movieService)
    }.flow.cachedIn(viewModelScope)

    fun navigateToMovieDetail(movie: Movie) {
        _stateNavigateToDetailMovie.value = movie
    }

    fun doneNavigateToMovieDetail() {
        _stateNavigateToDetailMovie.value = null
    }

}