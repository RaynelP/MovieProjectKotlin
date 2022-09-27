package com.raynel.alkemyproject.Repository.roomDataBase.daos

import androidx.room.*
import com.raynel.alkemyproject.model.FavoriteMovie

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM FavoriteMovie")
    suspend fun getAll(): List<FavoriteMovie>?

    @Query("SELECT * FROM FavoriteMovie WHERE idMovie == :id")
    suspend fun getGame(id: Long): FavoriteMovie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(movie: FavoriteMovie)

    @Delete
    suspend fun delete(movie: FavoriteMovie)
}