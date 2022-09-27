package com.raynel.challenge.Repository.Network.Impl

import com.raynel.alkemyproject.model.PageListMovies
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.model.MovieDetail
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import com.raynel.challenge.Repository.movieServices

class MovieServiceImpl private constructor(): IMoviesService {

    companion object{

        private val movieService: MovieServiceImpl? = null

        fun getInstance(): MovieServiceImpl{
            return movieService ?: MovieServiceImpl()
        }

    }
    override suspend fun getPopularMovies(page: Int): PageListMovies? {
        return movieServices.getPopularMovies(page)
    }

    override suspend fun getMovieDetails(idMovie: Int): MovieDetail? {
       return movieServices.getMovieDetails(idMovie)
    }

}