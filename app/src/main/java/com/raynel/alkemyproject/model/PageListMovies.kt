package com.raynel.alkemyproject.model

data class PageListMovies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Long,
    val total_results: Long
)

