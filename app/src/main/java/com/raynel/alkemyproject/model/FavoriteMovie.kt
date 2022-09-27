package com.raynel.alkemyproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovie(
    @PrimaryKey
    val idMovie: Long,

    val tittle: String,

    val image: String,

    val overView: String
    )