package com.raynel.alkemyproject.view.FavoritesFragment

import androidx.lifecycle.*
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.util.GenericViewModel
import com.raynel.alkemyproject.home.HomeViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoriteVIewModel(val favoriteMovieSource: FavoriteMovieDao): GenericViewModel() {

    private val _favoritesMovies: MutableLiveData<List<FavoriteMovie>> by lazy { MutableLiveData() }
    fun favoriteMovies(): LiveData<List<FavoriteMovie>> = _favoritesMovies


    init {
        onLoanding()
    }

    fun getAllFavoriteMovies(){
        viewModelScope.launch {

            try {
                val result = favoriteMovieSource.getAll()
                _favoritesMovies.value = result
                if(result == null){
                    onError()
                }
            }catch (e: Exception){
                onError()
            }
            doneLoanding()
        }
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