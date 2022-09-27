package com.raynel.challenge.Repository

import com.raynel.alkemyproject.model.PageListMovies
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.model.MovieDetail
import com.raynel.alkemyproject.model.Rating

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val base_url = "https://api.themoviedb.org/3/"
const val API_KEY = "0ad1649345d3ae87550f4571776c8aa5"

fun getRetrofit(): Retrofit{
    return Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val movieServices: RetrofitApiEndpoints by lazy {
    getRetrofit().create(RetrofitApiEndpoints::class.java)
}

interface RetrofitApiEndpoints {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int,
                                 @Query("api_key") api: String = API_KEY): PageListMovies

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") idMovie: Int,
                                @Query("api_key") api: String = API_KEY): MovieDetail

    @POST("/movie/{id}/rating")
    suspend fun setRating(@Path("id") idMovie: Int,
                          @Body rating: Rating,
                          @Query("api_key") api: String = API_KEY)
}