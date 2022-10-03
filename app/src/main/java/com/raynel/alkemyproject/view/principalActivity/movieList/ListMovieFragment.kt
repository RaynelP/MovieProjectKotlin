package com.raynel.alkemyproject.view.principalActivity.movieList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import com.raynel.alkemyproject.view.principalActivity.MainActivity
import com.raynel.challenge.Repository.Network.Impl.MovieServiceImpl
import com.raynel.challenge.ViewModel.MovieListViewModel
import com.raynel.challenge.ViewModel.MovieViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListMovieFragment : Fragment() {

    lateinit var binding : FragmentListBinding
    lateinit var movieListViewModel: MovieListViewModel
    lateinit var adapterRv: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentListBinding
            .inflate(inflater, container, false)

        setHasOptionsMenu(false)
        configViewModel()
        configList()
        configObservers()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun configViewModel() {
        //instance a service and put in the viewModel
        val movieService = MovieServiceImpl.getInstance()
        val factory = MovieViewModelFactory(movieService)

        //create viewModel
        movieListViewModel = ViewModelProvider(this, factory).get(MovieListViewModel::class.java)
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
                bundle.putInt("id", movie.id)
                this.findNavController().navigate(R.id.action_fragmentList_to_detailMovieFragment, bundle)
                movieListViewModel.doneNavigateToMovieDatail()
            }
        })

        movieListViewModel.isLoading().observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                val visibility = if(it){
                    View.VISIBLE
                } else{
                    View.GONE
                }
                (requireActivity() as MainActivity)
                    .showOrDoneProgressBar(visibility)
            }
        })


    }

    private fun configList(){
        adapterRv = MovieListAdapter(OnClickListener { movie ->
            movieListViewModel.navigateToMovieDatail(movie)
        })

        val gridLayout = GridLayoutManager(context, 3)

        binding.movieList.apply {
            adapter = adapterRv
            layoutManager = gridLayout
        }
    }

}
