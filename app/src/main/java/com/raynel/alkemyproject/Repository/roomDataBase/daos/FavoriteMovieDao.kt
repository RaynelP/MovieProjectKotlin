package com.raynel.alkemyproject.Repository.roomDataBase.daos

import androidx.room.*
import com.raynel.alkemyproject.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM FavoriteMovie")
    fun findAllFavoriteMovie(): Flow<List<FavoriteMovie>?>

    @Query("SELECT * FROM FavoriteMovie WHERE idMovie == :id")
    fun findFavoriteMovie(id: Long): Flow<FavoriteMovie?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(movie: FavoriteMovie)

    @Delete
    suspend fun delete(movie: FavoriteMovie)
}