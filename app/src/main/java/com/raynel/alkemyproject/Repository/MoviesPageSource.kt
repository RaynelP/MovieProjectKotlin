package com.raynel.alkemyproject.Repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Interface.IMoviesService
import java.io.IOException

class MoviesPageSource(private val backend: IMoviesService): PagingSource <Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key?: 1
            val response = backend.getPopularMovies(pageNumber)
            LoadResult.Page(
                data = response?.results?: emptyList(),
                prevKey = null,
                nextKey = pageNumber + 1
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}