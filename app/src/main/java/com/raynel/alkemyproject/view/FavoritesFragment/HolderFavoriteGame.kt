package com.raynel.alkemyproject.view.FavoritesFragment

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raynel.alkemyproject.databinding.ItemFavoriteMovieBinding
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.util.genericAdapter.OnclickItemListener
import com.raynel.alkemyproject.util.genericAdapter.ViewData
import com.squareup.picasso.Picasso

class HolderFavoriteGame: ViewData<FavoriteMovie, ItemFavoriteMovieBinding>{
    override fun bind(
        binding: ItemFavoriteMovieBinding?,
        item: FavoriteMovie?,
        onClickListener: OnclickItemListener<FavoriteMovie>?
    ) {
        binding?.let {
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${item?.image}")
                .into(it.movieImage)
            it.favoriteMovie = item
        }
    }

    override fun instanceBinding(parent: ViewGroup?): ItemFavoriteMovieBinding {
        val inflater = LayoutInflater.from(parent?.context)
        return ItemFavoriteMovieBinding
            .inflate(inflater, parent, false)
    }
}