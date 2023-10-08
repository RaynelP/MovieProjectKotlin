package com.raynel.alkemyproject.view.mainActivity.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentHomeBinding
import com.raynel.alkemyproject.model.Movie
import com.raynel.alkemyproject.util.genericAdapter.OnclickItemListener
import com.raynel.alkemyproject.view.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var upcomingAdapter: HomeListAdapter
    private lateinit var bestRatingAdapter: HomeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        configObservers()
        configLists()

        return binding.root
    }

    private fun configLists() {

        val onClick = OnClickListener { movie ->
            findNavController()
                .navigate(
                    R.id.action_homeFragment_to_detailMovieActivity,
                    Bundle().apply {
                        putLong("id", movie.id)
                    }
                )
        }

        upcomingAdapter = HomeListAdapter(onClick).apply {
            binding.upcomingSection.listTittle.text = "Upcomings"
            binding.upcomingSection.list.adapter = this@apply
        }

        bestRatingAdapter = HomeListAdapter(onClick).apply {
            binding.rankingSection.listTittle.text = "Ranking"
            binding.rankingSection.list.adapter = this@apply
        }
    }

    private fun configObservers() {

        homeViewModel.randomMovie().observe(viewLifecycleOwner, Observer { list ->

            list?.let {
                configViewPager(list)
                upcomingAdapter.submitList(list.subList(0, 5))
                bestRatingAdapter.submitList(list.subList(0, 5))
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