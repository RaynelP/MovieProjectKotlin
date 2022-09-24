package com.raynel.alkemyproject.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raynel.alkemyproject.databinding.ImageViewBinding
import com.raynel.alkemyproject.model.Movie
import com.squareup.picasso.Picasso

class ImagesViewPagerAdapter: RecyclerView.Adapter<ImagesViewPagerAdapter.ImagesViewHoder>() {

    var list: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHoder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageViewBinding.inflate(inflater, parent, false)
        return ImagesViewHoder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHoder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ImagesViewHoder(private val binding: ImageViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Picasso.get().load( "https://image.tmdb.org/t/p/w500${movie.backdrop_path}").into(binding.appCompatImageView);
        }
    }
}