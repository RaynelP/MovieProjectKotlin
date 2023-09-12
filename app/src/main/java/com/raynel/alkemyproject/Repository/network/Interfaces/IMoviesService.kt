package com.raynel.challenge.Repository.Network.Interface

import com.raynel.alkemyproject.model.PageListMovies
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.model.MovieDetail

interface IMoviesService {
    suspend fun getPopularMovies(page: Int = 1): PageListMovies?
    suspend fun getMovieDetails(idMovie: Long): MovieDetail?
}