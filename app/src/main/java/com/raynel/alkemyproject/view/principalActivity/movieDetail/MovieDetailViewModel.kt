package com.raynel.alkemyproject.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.model.MovieDetail
import com.raynel.alkemyproject.util.GenericViewModel
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailMovieViewModel(private val movieService: IMoviesService,
                           private val favoritesMovieService: FavoriteMovieDao,
                            private val idMovie: Int): GenericViewModel() {

    private val _detailMovie: MutableLiveData<MovieDetail> = MutableLiveData()
    fun detailMovie() = _detailMovie

    private val _isOnFavorite: MutableLiveData<Boolean?> = MutableLiveData()
    fun isOnFavorite(): LiveData<Boolean?> = _isOnFavorite

    private val _isAddedOrDeletedRightNow: MutableLiveData<Boolean?> = MutableLiveData()
    fun isAddedOrDeletedRightNow(): LiveData<Boolean?> = _isAddedOrDeletedRightNow

    private val _navigateToMovieWeb: MutableLiveData<MovieDetail> = MutableLiveData()
    fun navigateToMovieWeb(): LiveData<MovieDetail> = _navigateToMovieWeb


    init {
        getDetailMovie()
    }

    private fun getDetailMovie() {
        onLoanding()
        viewModelScope.launch {
            try {
                val movie: MovieDetail? = movieService.getMovieDetails(idMovie)
                _detailMovie.value = movie
                if(movie != null){
                    getAllFavoriteMovies()
                }
            } catch (e: Exception) {
                doneLoanding()
                onError()
            }
        }
    }

    fun getAllFavoriteMovies(){
        viewModelScope.launch() {

            try {
                val actualMovie = _detailMovie.value
                val idMovieActual = actualMovie?.id
                val result = favoritesMovieService.getGame(idMovieActual!!)

                _isOnFavorite.value = result != null

            }catch (e: Exception){
                e.message
            }

        }
    }
    val TAG = "roomDataBase"
    fun saveMovie(){
        viewModelScope.launch {

            try {
                favoritesMovieService
                    .save(convertMovieToFavoriteMovie())
                onAddedOnFavorite()
            }catch (e: Exception){
                e.message
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }

    fun deleteMovie(){
        viewModelScope.launch {

            try {
                favoritesMovieService
                    .delete(convertMovieToFavoriteMovie())
                onRemoveOnFavorite()
            }catch (e: Exception){
                e.message
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }

    private fun convertMovieToFavoriteMovie(): FavoriteMovie {
        val movieActual = _detailMovie.value!!
        return FavoriteMovie(
            movieActual.id,
            movieActual.title,
            movieActual.backdrop_path,
            movieActual.overview
        )
    }

    fun navigateToMovieWebBrowser() {
        _navigateToMovieWeb.value = _detailMovie.value
    }

    fun doneNavigationWebBrowser() {
        _navigateToMovieWeb.value = null
    }

    fun onRemoveOnFavorite(){
        _isOnFavorite.value = false
        _isAddedOrDeletedRightNow.value = false
    }

    fun onAddedOnFavorite(){
        _isOnFavorite.value = true
        _isAddedOrDeletedRightNow.value = true
    }

    fun doneAddedOrDelete(){
        _isAddedOrDeletedRightNow.value = null
    }
}

@Suppress("UNCHECKED_CAST")
class DetailMovieViewModelFactory(private val movieService: IMoviesService,
                                  private val favoritesMovieService: FavoriteMovieDao,
                                  private val idMovie: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            return DetailMovieViewModel(movieService, favoritesMovieService, idMovie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

