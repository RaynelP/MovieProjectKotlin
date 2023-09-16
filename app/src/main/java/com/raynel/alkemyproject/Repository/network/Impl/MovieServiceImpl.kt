package com.raynel.challenge.Repository.Network.Impl

import com.raynel.alkemyproject.model.PageListMovies
import com.raynel.alkemyproject.model.MovieDetail
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import com.raynel.challenge.Repository.RetrofitApiEndpoints
import javax.inject.Inject

class MovieServiceImpl @Inject constructor(
    private val movieApiRetrofit: RetrofitApiEndpoints
) : IMoviesService {

    override suspend fun getPopularMovies(page: Int): PageListMovies? {
        return movieApiRetrofit.getPopularMovies(page)
    }

    override suspend fun getMovieDetails(idMovie: Long): MovieDetail? {
        return movieApiRetrofit.getMovieDetails(idMovie)
    }

}