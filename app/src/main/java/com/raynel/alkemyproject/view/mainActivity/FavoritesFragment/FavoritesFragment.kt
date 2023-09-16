package com.raynel.alkemyproject.view.mainActivity.FavoritesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.raynel.alkemyproject.databinding.FragmentFavoritesBinding
import com.raynel.alkemyproject.databinding.ItemFavoriteMovieBinding
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.showMessageWithSnackBar
import com.raynel.alkemyproject.util.genericAdapter.GenericAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoriteVIewModel
    private lateinit var adapter: GenericAdapter<FavoriteMovie, ItemFavoriteMovieBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoriteVIewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritesBinding
            .inflate(inflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configObservers()
    }

    private fun configObservers() {
        adapter = GenericAdapter
            .Builder<FavoriteMovie, ItemFavoriteMovieBinding>()
            .addList(null)
            .addViewData(HolderFavoriteGame())
            .build()

        // invoke a flow that is connect to database
        lifecycleScope.launch(){
            viewModel.getAllFavoriteMovies()
                .collect{list ->

                    list?.let {
                        adapter.addList(list)
                        binding.rvFavoriteGameList.adapter = adapter
                        configSwipe(adapter)
                    }

                }
        }

    }

    private fun configSwipe(adapter: GenericAdapter<FavoriteMovie, ItemFavoriteMovieBinding>){

        val callBack = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = viewHolder.adapterPosition

                viewModel.deleteMovie(adapter.getItemList(item))

                showMessageWithSnackBar(
                    context!!,
                    binding.root,
                    adapter.getItemList(item).tittle + " has been deleted"
                )

                adapter.deleteItem(item)
            }

        }

        val itemTouch = ItemTouchHelper(callBack)
        itemTouch.attachToRecyclerView(binding.rvFavoriteGameList)
    }

}