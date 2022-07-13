package com.raynel.alkemyproject.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.raynel.alkemyproject.movieList.ImagesViewPagerAdapter
import com.raynel.alkemyproject.databinding.ImagesBinding
import com.raynel.alkemyproject.databinding.ListItemMovieBinding
import com.raynel.alkemyproject.model.Movie
import com.squareup.picasso.Picasso

class MovieListAdapter(private val onClickListener: OnClickListener)
    : PagingDataAdapter<Movie, MovieViewHolder>(MovieListDiff()) {

    val adapterVP: ImagesViewPagerAdapter = ImagesViewPagerAdapter()
    val list = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == ITEM_1){
            val binding = ImagesBinding.inflate(inflater, parent, false)
            binding.imagesViewPager.adapter = adapterVP
            MovieViewHolder(binding)
        }else{
          val binding = ListItemMovieBinding.inflate(inflater, parent, false)
          binding.onclickListener = onClickListener
          return MovieViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = if(position == 0) getItem(position) else getItem(position - 1)
        holder.bind(item)
        if(position in 1..3){
            item?.let { adapterVP.list.add(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) ITEM_1 else ITEM_2
    }

    companion object {
        const val ITEM_1 = 1
        const val ITEM_2 = 2
    }
}

class MovieViewHolder (private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(m: Movie?){
        if(binding is ListItemMovieBinding){
            binding.apply {
                this.movie = m
                Picasso.get().load( "https://image.tmdb.org/t/p/w500${m?.backdrop_path}").into(movieImage);
            }
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

