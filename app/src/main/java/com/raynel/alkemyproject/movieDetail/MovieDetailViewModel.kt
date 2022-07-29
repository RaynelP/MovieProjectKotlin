package com.raynel.alkemyproject.viewModel

import androidx.lifecycle.*
import com.raynel.alkemyproject.model.MovieDetail
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailMovieViewModel(private val movieService: IMoviesService,
                            private val idMovie: Int): ViewModel() {

    private val _detailMovie: MutableLiveData<MovieDetail> = MutableLiveData()
    fun detailMovie() = _detailMovie

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun showLoading(): LiveData<Boolean> = _showLoading

    private val _navigateToMovieWeb: MutableLiveData<MovieDetail> = MutableLiveData()
    fun navigateToMovieWeb(): LiveData<MovieDetail> = _navigateToMovieWeb

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    fun error(): LiveData<Boolean> = _error

    init {
        _showLoading.value = false
        getDetailMovie()
    }

    private fun getDetailMovie() {
        _showLoading.value = true
        viewModelScope.launch {
            try {
                val movie: MovieDetail? = movieService.getMovieDetails(idMovie)
                _detailMovie.value = movie
            } catch (e: Exception) {
                _showLoading.value = false
                _error.value = true
            }
        }
    }

    fun doneShowLoanding() {
        _showLoading.value = false
    }

    fun navigateToMovieWebBrowser() {
        _navigateToMovieWeb.value = _detailMovie.value
    }

    fun doneNavigationWebBrowser() {
        _navigateToMovieWeb.value = null
    }

    fun retry(){
        _showLoading.value = false
        getDetailMovie()
    }

}

@Suppress("UNCHECKED_CAST")
class DetailMovieViewModelFactory(private val movieService: IMoviesService,
                                  private val idMovie: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            return DetailMovieViewModel(movieService, idMovie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

