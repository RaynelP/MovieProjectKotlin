package com.raynel.alkemyproject.view.movieDetailActivity

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.Repository.roomDataBase.dataBase.AppDataBase
import com.raynel.alkemyproject.databinding.FragmentDetailMovieBinding
import com.raynel.alkemyproject.formatDescriptionAndGenres
import com.raynel.alkemyproject.formatInfoMovie
import com.raynel.alkemyproject.viewModel.DetailMovieViewModel
import com.raynel.alkemyproject.viewModel.DetailMovieViewModelFactory
import com.raynel.challenge.Repository.Network.Impl.MovieServiceImpl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var movieDetailViewModel: DetailMovieViewModel
    private var movieId: Long? = null
    private var isFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding
            .inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViewModel()
        configObservers()
    }

    private fun configViewModel() {
        val activity = requireActivity() as MovieDetailActivity
        movieId = activity.movieId
        val movieService = MovieServiceImpl.getInstance()
        val favoritesMovieService = AppDataBase.getInstance(context!!)?.favoriteMoviesDAO()
        val factory = DetailMovieViewModelFactory(movieService, favoritesMovieService!!)

        movieDetailViewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        binding.detailViewModel = movieDetailViewModel
    }

    private fun configObservers() {
        movieDetailViewModel.apply {

            // find the detail movie info
            getMovieDetail(movieId!!)

            detailMovie.observe(viewLifecycleOwner) { movie ->

                movie?.let {
                    val context = context
                    binding.apply {
                        this.movie = movie
                        textGenreDescription.text = formatDescriptionAndGenres(movie, context!!)
                        infoMovie.text = formatInfoMovie(movie, context)
                        Picasso.get()
                            .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                            .into(imageView)

                        doneLoanding()
                    }
                }
            }

            isLoading().observe(viewLifecycleOwner, Observer { show ->
                show?.let {
                    binding.progressBar.visibility = if(show) View.VISIBLE else View.GONE
                }
            })

            navigateToMovieWeb.observe(viewLifecycleOwner, Observer { movie ->
                movie?.let {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
                    startActivity(browserIntent)

                    doneNavigationWebBrowserEvent()
                }
            })

            error().observe(viewLifecycleOwner, Observer { error ->
                error?.let {
                    if(error){
                        val messageError = Snackbar.make(
                            binding.root,
                            getString(R.string.error_network),
                            Snackbar.LENGTH_INDEFINITE
                        )
                        messageError.setAction("retry", View.OnClickListener {
                            movieDetailViewModel.retry()
                        })
                        messageError.show()
                    }
                }
            })

            viewModelScope.launch {
                isFavorite(movieId = movieId!!).collect{ favorite ->
                    favorite?.let {
                        isFavorite = favorite
                        paintItemFavorite(favorite)
                    }
                }
            }

            onFavoriteEvent.observe(viewLifecycleOwner, Observer { favoriteEvent ->

                favoriteEvent?.let {
                    if(isFavorite){
                        movieDetailViewModel.deleteMovie()
                    } else {
                        movieDetailViewModel.saveMovie()
                    }

                }

            })

        }
    }

    fun paintItemFavorite(isFavorite: Boolean){
        val icon = if(isFavorite){
            R.drawable.icom_favorite_on
        }else{
            R.drawable.icom_favorite_off
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.favoriteBottom.foreground  = resources.getDrawable(icon)
        }
    }

}