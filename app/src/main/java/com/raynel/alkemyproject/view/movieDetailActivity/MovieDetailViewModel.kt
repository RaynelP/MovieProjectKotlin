package com.raynel.alkemyproject.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.model.MovieDetail
import com.raynel.alkemyproject.util.GenericViewModel
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieService: IMoviesService,
    private val favoritesMovieService: FavoriteMovieDao
): GenericViewModel() {

    private val TAG = "roomDataBase"

    // livedata detailMovie event
    private val _detailMovie: MutableLiveData<MovieDetail?> = MutableLiveData()
    val detailMovie: LiveData<MovieDetail?> = _detailMovie

    // livedata navigate to web event
    private val _navigateToMovieWeb: MutableLiveData<MovieDetail?> = MutableLiveData()
    val navigateToMovieWeb: LiveData<MovieDetail?> = _navigateToMovieWeb

    // livedata onFavorite event
    private val _onFavoriteEvent: MutableLiveData<Boolean?> = MutableLiveData()
    val onFavoriteEvent: LiveData<Boolean?> = _onFavoriteEvent

    fun getMovieDetail(movieId: Long) {
        onLoanding()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movie = movieService.getMovieDetails(movieId) ?: throw Exception()
                _detailMovie.postValue(movie)
            } catch (e: Exception){
                withContext(Dispatchers.Main){ onError() }
            }
        }
    }

    suspend fun isFavorite(movieId: Long): Flow<Boolean> =
        favoritesMovieService
            .findFavoriteMovie(movieId)
            .map { movie ->
                movie != null
            }
            .catch{
                withContext(Dispatchers.Main){ onError() }
            }

    fun saveMovie(){
        viewModelScope.launch {
            try {
                favoritesMovieService
                    .save(convertMovieToFavoriteMovie())
            }catch (e: Exception){
                e.message?.let {
                    Log.d(TAG, it)
                }
            }
        }
    }

    fun deleteMovie(){
        viewModelScope.launch {
            try {
                favoritesMovieService
                    .delete(convertMovieToFavoriteMovie())
            }catch (e: Exception){
                e.message?.let {
                    Log.d(TAG, it)
                }
            }
        }
    }

    private fun convertMovieToFavoriteMovie(): FavoriteMovie {
        val movie = detailMovie.value!!
        return FavoriteMovie(
            movie.id,
            movie.title,
            movie.backdrop_path,
            movie.overview
        )
    }

    fun doOnNavigateToMovieWebBrowserEvent() {
        _navigateToMovieWeb.value = detailMovie.value
    }

    fun doneNavigationWebBrowserEvent() {
        _navigateToMovieWeb.value = null
    }

    fun doOnFavoriteEvent(){
        _onFavoriteEvent.value = true
    }

    fun doneOnFavoriteEvent(){
        _onFavoriteEvent.value = null
    }

}