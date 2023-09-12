package com.raynel.alkemyproject.view.principalActivity.FavoritesFragment

import androidx.lifecycle.*
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.util.GenericViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FavoriteVIewModel(private val favoriteMovieSource: FavoriteMovieDao): GenericViewModel() {

    fun getAllFavoriteMovies() =
        favoriteMovieSource
            .findAllFavoriteMovie()
            .onStart{
                onLoanding()
            }
            .onCompletion {
                doneLoanding()
            }
            .catch {
                withContext(Dispatchers.Main){ onError() }
            }

    fun saveMovie(movie: FavoriteMovie){
        viewModelScope.launch {

            try {
                favoriteMovieSource.save(movie)
            }catch (e: Exception){
                onError()
            }
        }
    }

    fun deleteMovie(movie: FavoriteMovie){
        viewModelScope.launch {

            try {
                favoriteMovieSource.delete(movie)
            }catch (e: Exception){
                onError()
            }

        }
    }

}

class FavoriteViewModelFactory(val favoriteMovieSource: FavoriteMovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteVIewModel::class.java)) {
            return FavoriteVIewModel(favoriteMovieSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}