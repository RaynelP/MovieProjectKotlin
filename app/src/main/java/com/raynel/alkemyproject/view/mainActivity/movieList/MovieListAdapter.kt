package com.raynel.alkemyproject.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raynel.alkemyproject.databinding.ItemListMovieBinding
import com.raynel.alkemyproject.model.Movie
import com.squareup.picasso.Picasso

class MovieListAdapter(private val onClickListener: OnClickListener)
    : PagingDataAdapter<Movie, MovieViewHolder>(MovieListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemListMovieBinding
            .inflate(inflater, parent, false)

        binding.onclickListener = onClickListener

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class MovieViewHolder (private val binding: ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(m: Movie?){
        binding.apply {
            movie = m
            Picasso.get()
                .load( "https://image.tmdb.org/t/p/w500${m?.backdrop_path}")
                .into(movieImage);
        }
    }
}

class MovieListDiff: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return newItem == oldItem
    }
}

class OnClickListener(val onClickLambda: (movie: Movie) -> Unit){
    fun onClick(movie: Movie){
        onClickLambda(movie)
    }
}