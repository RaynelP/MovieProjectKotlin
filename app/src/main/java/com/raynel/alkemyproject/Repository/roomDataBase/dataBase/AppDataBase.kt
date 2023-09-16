package com.raynel.alkemyproject.Repository.roomDataBase.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.model.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 6)
abstract class AppDataBase : RoomDatabase() {

    abstract fun favoriteMoviesDAO(): FavoriteMovieDao

    companion object{
        private var db: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase?{
            if (db == null){
                db = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "movies-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return db
        }

    }

}

