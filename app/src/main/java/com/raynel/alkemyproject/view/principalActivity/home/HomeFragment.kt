package com.raynel.alkemyproject.view.principalActivity.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.raynel.alkemyproject.databinding.FragmentHomeBinding
import com.raynel.alkemyproject.model.Movie
import com.raynel.challenge.Repository.Network.Impl.MovieServiceImpl

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding
            .inflate(inflater, container, false)

        configViewModel()
        configObservers()

        return binding.root
    }

    private fun configViewModel() {
        //instance a service
        val movieService = MovieServiceImpl.getInstance()
        //instance a service
        val factory = HomeViewModelFactory(movieService)

        //create viewModel
        homeViewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)
    }

    private fun configObservers() {
        homeViewModel.randomMovie().observe(viewLifecycleOwner, Observer { list ->

            list?.let {
                configViewPager(list)
            }

        })

        homeViewModel.isLoading().observe(viewLifecycleOwner, Observer { isLoading ->

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

    private fun configViewPager(randomList: List<Movie>) {
        val adapter = ImagesViewPagerAdapter()
        adapter.list.addAll(randomList.subList(0, 5))
        binding.imagesViewPager.adapter = adapter
    }

}