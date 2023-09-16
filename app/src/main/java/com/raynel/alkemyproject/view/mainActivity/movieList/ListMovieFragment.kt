package com.raynel.alkemyproject.view.mainActivity.movieList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentListBinding
import com.raynel.alkemyproject.view.MovieListAdapter
import com.raynel.alkemyproject.view.OnClickListener
import com.raynel.alkemyproject.view.mainActivity.FavoritesFragment.FavoriteVIewModel
import com.raynel.challenge.ViewModel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListMovieFragment : Fragment() {

    private lateinit var binding : FragmentListBinding
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var adapterRv: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentListBinding
            .inflate(inflater, container, false)

        setHasOptionsMenu(false)
        configList()
        configObservers()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun configObservers() {

        lifecycleScope.launch {
            movieListViewModel.flow.collectLatest { pagingData ->
                adapterRv.submitData(pagingData)
            }
        }

        movieListViewModel.retry().observe(viewLifecycleOwner, Observer { retry ->
            retry?.let {
                adapterRv.retry()
                movieListViewModel.doneRetry()
            }
        })

        movieListViewModel.error().observe(viewLifecycleOwner, Observer { showError ->
            showError?.let {
                 val error = Snackbar.make(binding.root,
                     getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE)
                 error.setAction("retry", View.OnClickListener {
                     movieListViewModel.startRetry()
                 })
                 error.show()
             }
        })

        lifecycleScope.launch {
            adapterRv.loadStateFlow.collectLatest { loadState ->
                if(loadState.refresh is LoadState.Error){
                    movieListViewModel.onError()
                }
            }
        }

        movieListViewModel.stateNavigateToDetailMovie().observe(viewLifecycleOwner, Observer { movie ->
            //navigate to detail movie
            movie?.let {
                val bundle = Bundle()
                bundle.putLong("id", movie.id)
                findNavController().navigate(R.id.action_searchFragment_to_detailMovieActivity, bundle)
                movieListViewModel.doneNavigateToMovieDetail()
            }
        })

        movieListViewModel.isLoading().observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                val visibility = if(isLoading){
                    View.VISIBLE
                }else{
                    View.GONE
                }
                binding.progressBar.visibility = visibility
            }
        })

    }

    private fun configList(){
        adapterRv = MovieListAdapter(OnClickListener { movie ->
            movieListViewModel.navigateToMovieDetail(movie)
        })

        val gridLayout = GridLayoutManager(context, 3)

        binding.movieList.apply {
            adapter = adapterRv
            layoutManager = gridLayout
        }
    }

}
