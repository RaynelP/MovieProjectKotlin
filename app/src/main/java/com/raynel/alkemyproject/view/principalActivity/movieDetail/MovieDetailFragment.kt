package com.raynel.alkemyproject.view.principalActivity.movieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.Repository.roomDataBase.dataBase.AppDataBase
import com.raynel.alkemyproject.databinding.FragmentDetailMovieBinding
import com.raynel.alkemyproject.formatDescriptionAndGenres
import com.raynel.alkemyproject.formatInfoMovie
import com.raynel.alkemyproject.showMessageWithSnackBar
import com.raynel.alkemyproject.view.principalActivity.MainActivity
import com.raynel.alkemyproject.viewModel.DetailMovieViewModel
import com.raynel.alkemyproject.viewModel.DetailMovieViewModelFactory
import com.raynel.challenge.Repository.Network.Impl.MovieServiceImpl
import com.squareup.picasso.Picasso


class MovieDetailFragment : Fragment() {

    lateinit var binding: FragmentDetailMovieBinding
    lateinit var movieDetailViewModel: DetailMovieViewModel
    lateinit var favoriteItem: MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        configViewModel()
        configObservers()

        return binding.root
    }

    private fun configViewModel() {
        val idMovie = requireArguments().getInt("id")
        val movieService = MovieServiceImpl.getInstance()
        val favoritesMovieService = AppDataBase.getInstance(context!!)?.favoriteMoviesDAO()
        val factory = DetailMovieViewModelFactory(movieService, favoritesMovieService!!, idMovie)

        movieDetailViewModel = ViewModelProvider(this, factory)
            .get(DetailMovieViewModel::class.java)
        binding.detailViewModel = movieDetailViewModel
    }

    private fun configObservers() {
        movieDetailViewModel.apply {
            detailMovie().observe(viewLifecycleOwner, Observer{ movie ->

               movie?.let {
                   val context = context
                   binding.apply {
                       this.movie = movie
                       textGenreDescription.text = formatDescriptionAndGenres(movie, context!!)
                       infoMovie.text = formatInfoMovie(movie, context)
                       Picasso.get()
                           .load( "https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                           .into(imageView)
                   }
                   doneLoanding()
               }

            })

            isLoading().observe(viewLifecycleOwner, Observer { show ->
                show?.let {
                    val visibility = if(it){
                        View.VISIBLE
                    } else{
                        View.GONE
                    }
                    (requireActivity() as MainActivity)
                        .showOrDoneProgressBar(visibility)
                }
            })

            navigateToMovieWeb().observe(viewLifecycleOwner, Observer { movie ->
                movie?.let {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
                    startActivity(browserIntent)

                    doneNavigationWebBrowser()
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



        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater
            .inflate(R.menu.menu_favorite, menu)
        favoriteItem = menu.getItem(0)

        movieDetailViewModel.isAddedOrDeletedRightNow().observe(viewLifecycleOwner, Observer {
            it?.let {
                val message = if(it){
                    " has been added"
                }else{
                    " has been deleted"
                }

                showMessageWithSnackBar(
                    context!!,
                    binding.root,
                    movieDetailViewModel.detailMovie().value?.title + message)

                movieDetailViewModel.doneAddedOrDelete()

            }

        })

        movieDetailViewModel.isOnFavorite().observe(viewLifecycleOwner, Observer { isFavorite ->
            isFavorite?.let {
                paintItemFavorite(isFavorite)
            }
        })

    }

    fun paintItemFavorite(isFavorite: Boolean){
        val icon = if(isFavorite){
            R.drawable.icom_favorite_on
        }else{
            R.drawable.icom_favorite_off
        }

        favoriteItem.icon  = resources.getDrawable(icon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.bottomFavorite){
            val isFavorite = movieDetailViewModel.isOnFavorite().value
            if(isFavorite == false){
                movieDetailViewModel.saveMovie()
            }
            if(isFavorite == true){
                movieDetailViewModel.deleteMovie()
            }

        }

        return true
    }

}