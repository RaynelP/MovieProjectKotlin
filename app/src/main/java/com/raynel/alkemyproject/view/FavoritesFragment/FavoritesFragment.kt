package com.raynel.alkemyproject.view.FavoritesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        configViewModel()
        configObservers()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllFavoriteMovies()
    }

    private fun configObservers() {
        adapter = GenericAdapter
            .Builder<FavoriteMovie, ItemFavoriteMovieBinding>()
            .addList(null)
            .addViewData(HolderFavoriteGame())
            .build()

        viewModel.favoriteMovies().observe(viewLifecycleOwner, Observer { list ->

            list?.let {
                adapter.addList(list)
                binding.rvFavoriteGameList.adapter = adapter
                configSwipe(adapter)
            }
        })
    }

    private fun configViewModel() {
        //instancio la base de datos
        val db = AppDataBase.getInstance(context!!)

        //instancio el dao
        val source = db!!.favoriteMoviesDAO()

        val factory = FavoriteViewModelFactory(source)

        viewModel = ViewModelProvider(this, factory).get(FavoriteVIewModel::class.java)
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