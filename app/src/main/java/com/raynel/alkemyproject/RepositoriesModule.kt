package com.raynel.alkemyproject

import com.raynel.challenge.Repository.Network.Impl.MovieServiceImpl
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindMovieRepo(
        movieRepository: MovieServiceImpl
    ): IMoviesService

}