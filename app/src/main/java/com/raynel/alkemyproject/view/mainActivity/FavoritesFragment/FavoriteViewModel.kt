package com.raynel.alkemyproject.view.mainActivity.FavoritesFragment

import androidx.lifecycle.*
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.Repository.roomDataBase.dataBase.AppDataBase
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.util.GenericViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FavoriteVIewModel @Inject constructor(
    private val favoriteMovieSource: FavoriteMovieDao
): GenericViewModel() {

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