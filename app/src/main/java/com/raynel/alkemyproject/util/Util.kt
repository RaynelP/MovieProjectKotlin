package com.raynel.alkemyproject

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.core.text.set
import com.google.android.material.snackbar.Snackbar
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.model.MovieDetail


fun formatInfoMovie(movie: MovieDetail, context: Context): SpannableStringBuilder{
    var producersCountry = ""
    val listProductionCountry = movie.production_countries
    listProductionCountry.forEachIndexed { index, productionCountry ->
        producersCountry += productionCountry.name +
                if(index < listProductionCountry.size - 1) ", " else ""
    }
    val resources = context.resources
    val map = mapOf<String, String>(
        resources.getString(R.string.relase_date) to movie.release_date,
        resources.getString(R.string.language_origin) to  movie.original_language,
        resources.getString(R.string.status) to movie.status,
        resources.getString(R.string.production_countries) to producersCountry,
        resources.getString(R.string.home_page) to movie.homepage
    )
    val spannableString = SpannableStringBuilder("")

    map.forEach { m ->
        val newText = "${m.key}\n ${m.value}\n\n"
        spannableString.append(newText)
    }
    return spannableString
}

fun formatDescriptionAndGenres(movie: MovieDetail, context: Context): SpannableString{
    val listGenre = movie.genres
    var genres = ""
    listGenre.forEachIndexed { index, genre ->
        genres += genre.name +
                (if (index < listGenre.size - 1) ", " else "")
    }
    val text = "${genres}\n\n" +
               "${movie.overview}\n\n"

    val spannable = SpannableString(text)
    return spannable
}

fun showMessageWithSnackBar(context: Context, view: View, message: String){
    Toast
        .makeText(context, message, Toast.LENGTH_SHORT)
        .show()
    //Snackbar
    //    .make(view, menssage, Snackbar.LENGTH_SHORT)
    //    .show()
}//
