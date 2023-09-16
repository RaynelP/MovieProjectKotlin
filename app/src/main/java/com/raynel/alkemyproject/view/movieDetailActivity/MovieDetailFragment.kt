package com.raynel.alkemyproject.view.movieDetailActivity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentDetailMovieBinding
import com.raynel.alkemyproject.formatDescriptionAndGenres
import com.raynel.alkemyproject.formatInfoMovie
import com.raynel.alkemyproject.viewModel.MovieDetailViewModel
import com.raynel.challenge.ViewModel.MovieListViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private var movieId: Long? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        val activity = requireActivity() as MovieDetailActivity
        movieId = activity.movieId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding
            .inflate(inflater, container, false)
        binding.detailViewModel = movieDetailViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configObservers()
    }

    private fun configObservers() {
        movieDetailViewModel.apply {

            // find the detail movie info
            getMovieDetail(movieId!!)

            // movie observer
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

            // onLoading Observer
            isLoading().observe(viewLifecycleOwner, Observer { show ->
                show?.let {
                    binding.progressBar.visibility = if(show) View.VISIBLE else View.GONE
                }
            })

            // onNavigateToWebObserver
            navigateToMovieWeb.observe(viewLifecycleOwner, Observer { movie ->
                movie?.let {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
                    startActivity(browserIntent)

                    doneNavigationWebBrowserEvent()
                }
            })

            // on error observer
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

            // isFavorite state observer
            viewModelScope.launch {
                isFavorite(movieId = movieId!!).collect{ favorite ->
                    favorite?.let {
                        isFavorite = favorite
                        paintItemFavorite(favorite)
                    }
                }
            }

            // favorite event observer
            onFavoriteEvent.observe(viewLifecycleOwner){ favoriteEvent ->

                favoriteEvent?.let {
                    val i = 0
                    if(isFavorite){
                        movieDetailViewModel.deleteMovie()
                    } else {
                        movieDetailViewModel.saveMovie()
                    }
                    doneOnFavoriteEvent()
                }

            }

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