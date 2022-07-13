package com.raynel.alkemyproject.movieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentDetailMovieBinding
import com.raynel.alkemyproject.formatDescriptionAndGenres
import com.raynel.alkemyproject.formatInfoMovie
import com.raynel.alkemyproject.viewModel.DetailMovieViewModel
import com.raynel.alkemyproject.viewModel.DetailMovieViewModelFactory
import com.raynel.challenge.Repository.Network.Impl.MovieServiceImpl
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {

    lateinit var binding: FragmentDetailMovieBinding
    lateinit var movieDetailViewModel: DetailMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)

        configViewModel()
        configObservers()

        return binding.root
    }

    private fun configViewModel() {
        val idMovie = requireArguments().getInt("id")
        val movieService = MovieServiceImpl.getInstance()
        val factory = DetailMovieViewModelFactory(movieService, idMovie)

        movieDetailViewModel = ViewModelProvider(this, factory)
            .get(DetailMovieViewModel::class.java)
        binding.detailViewModel = movieDetailViewModel
    }

    private fun configObservers() {
        movieDetailViewModel.apply {
            detailMovie().observe(viewLifecycleOwner, Observer{ movie ->
                val context = context
                binding.apply {
                    this.movie = movie
                    textGenreDescription.text = formatDescriptionAndGenres(movie, context!!)
                    infoMovie.text = formatInfoMovie(movie, context)
                    Picasso.get()
                        .load( "https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                        .into(imageView)
                }

                doneShowLoanding()
            })

            showLoading().observe(viewLifecycleOwner, Observer { show ->
                show?.let {
                    if(show) binding.progressBar.visibility = View.VISIBLE
                    else binding.progressBar.visibility = View.GONE
                }
            })

            navigateToMovieWeb().observe(viewLifecycleOwner, Observer { movie ->
                movie?.let {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
                    startActivity(browserIntent)

                    doneNavigationWebBrowser()
                }
            })

        }
    }
}