package com.raynel.alkemyproject.view.mainActivity.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raynel.alkemyproject.databinding.ItemHomeViewBinding
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.view.MovieListDiff
import com.raynel.alkemyproject.view.OnClickListener
import com.squareup.picasso.Picasso

class HomeListAdapter(private val onClickListener: OnClickListener)
    : ListAdapter<Movie, UpcomingsViewHolder>(MovieListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemHomeViewBinding
            .inflate(inflater, parent, false)

        binding.onclickListener = onClickListener

        return UpcomingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class UpcomingsViewHolder (private val binding: ItemHomeViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(m: Movie?){
        binding.apply {
            movie = m
            Picasso.get()
                .load( "https://image.tmdb.org/t/p/w500${m?.backdrop_path}")
                .into(movieImage);
        }
    }
}