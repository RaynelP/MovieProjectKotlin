package com.raynel.alkemyproject.Repository.network.Interfaces

import com.raynel.alkemyproject.model.FavoriteMovie

interface IFavoriteMovies {

    suspend fun getAllGames(): List<FavoriteMovie>

    suspend fun saveFavoriteMovie()
}