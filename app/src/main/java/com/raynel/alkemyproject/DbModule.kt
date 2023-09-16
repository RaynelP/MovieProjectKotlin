package com.raynel.alkemyproject

import android.content.Context
import androidx.room.Room
import com.raynel.alkemyproject.Repository.roomDataBase.daos.FavoriteMovieDao
import com.raynel.alkemyproject.Repository.roomDataBase.dataBase.AppDataBase
import com.raynel.challenge.Repository.RetrofitApiEndpoints
import com.raynel.challenge.Repository.base_url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideMovieRepoRetrofit(): RetrofitApiEndpoints {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApiEndpoints::class.java)
    }

    @Singleton
    @Provides
    fun roomDatabaseProvider(
        @ApplicationContext context: Context
    ): AppDataBase {

        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "movies-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun favoriteMoviesDaoProvide(
        appDataBase: AppDataBase
    ): FavoriteMovieDao {
        return appDataBase.favoriteMoviesDAO()
    }

}