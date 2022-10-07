package com.raynel.alkemyproject.view.principalActivity.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentUserBinding
import com.raynel.alkemyproject.view.principalActivity.MainActivity

class UserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //inflate the dataBinding
        binding = FragmentUserBinding
            .inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.viewModel = viewModel

        configObservers()
    }


    private fun configObservers() {
        viewModel.apply {

            //observable of navigation to Dialog
            navigateToDialog().observe(viewLifecycleOwner, Observer{ navigate ->
                navigate?.let {
                    if(it){
                        showActualImageOrLoadImageDialog().show(
                            parentFragmentManager,
                            "options"
                        )

                    }
                    doneNavigateToDialog()
                }
            })

            //observable of the button LogOut
            signOut().observe(viewLifecycleOwner, Observer{ onSignOutPressed ->

                onSignOutPressed?.let {
                    //launch the dialog for confirm or cancel
                    OnSignOutDialogFragment()
                        .show(parentFragmentManager, "confirm")

                }

            })

        }
    }


}