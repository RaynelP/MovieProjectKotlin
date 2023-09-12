package com.raynel.alkemyproject.view.loginActivity.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentLoginBinding
import com.raynel.alkemyproject.view.loginActivity.LoginActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding
            .inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configViewModel()

        configObservers()

        binding.viewModel = viewModel
    }

    private fun configObservers() {
        viewModel.apply {
            navigateToSignUp().observe(viewLifecycleOwner, Observer{ navigate ->

                navigate?.let {
                    findNavController()
                        .navigate(R.id.action_LoginFragment_to_SignFragment)
                    doneNavigateToSignUp()
                }

            })

            logInSuccess().observe(viewLifecycleOwner, Observer {
                val activity = requireActivity() as  LoginActivity
                activity.onLogInUser(
                    binding.textInputEmail.text.toString(),
                    binding.textInputPassword.text.toString()
                )
            })
        }
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}