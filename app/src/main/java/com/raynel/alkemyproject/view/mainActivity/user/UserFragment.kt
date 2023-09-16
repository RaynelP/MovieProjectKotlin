package com.raynel.alkemyproject.view.mainActivity.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raynel.alkemyproject.databinding.FragmentUserBinding
import com.raynel.alkemyproject.view.mainActivity.MainActivity

class UserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding
            .inflate(inflater, container, false)

        binding.ButtonLogOut.setOnClickListener {
            val activity = requireActivity() as MainActivity
            activity.signUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

    }

}