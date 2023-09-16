package com.raynel.alkemyproject.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.raynel.alkemyproject.R

open class GenericFragment<T : ViewDataBinding>: Fragment() {

    private lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater
            .inflate(
                R.layout.fragment_favorites,
                container,
                false
            )
    }

}