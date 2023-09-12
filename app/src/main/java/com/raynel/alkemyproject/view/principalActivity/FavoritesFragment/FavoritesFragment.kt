package com.raynel.alkemyproject.view.principalActivity.FavoritesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.Repository.roomDataBase.dataBase.AppDataBase
import com.raynel.alkemyproject.databinding.FragmentFavoritesBinding
import com.raynel.alkemyproject.databinding.ItemFavoriteMovieBinding
import com.raynel.alkemyproject.model.FavoriteMovie
import com.raynel.alkemyproject.showMessageWithSnackBar
import com.raynel.alkemyproject.util.GenericFragment
import com.raynel.alkemyproject.util.genericAdapter.GenericAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesFragment : GenericFragment<FragmentFavoritesBinding>() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoriteVIewModel
    private lateinit var adapter: GenericAdapter<FavoriteMovie, ItemFavoriteMovieBinding>

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
        configViewModel()
        configObservers()
    }

    private fun configObservers() {
        adapter = GenericAdapter
            .Builder<FavoriteMovie, ItemFavoriteMovieBinding>()
            .addList(null)
            .addViewData(HolderFavoriteGame())
            .build()

        // invoke a flow that is connect to database
        lifecycleScope.launch(Dispatchers.IO){
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

    private fun configViewModel() {
        val db = AppDataBase.getInstance(context!!)

        val source = db!!.favoriteMoviesDAO()
        val factory = FavoriteViewModelFactory(source)
        viewModel = ViewModelProvider(this, factory)[FavoriteVIewModel::class.java]
    }

    fun configSwipe(adapter: GenericAdapter<FavoriteMovie, ItemFavoriteMovieBinding>){

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