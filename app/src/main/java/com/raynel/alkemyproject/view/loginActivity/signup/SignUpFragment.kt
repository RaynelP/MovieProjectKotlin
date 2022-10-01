package com.raynel.alkemyproject.view.loginActivity.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentSignUpBinding
import com.raynel.alkemyproject.view.loginActivity.LoginActivity
import com.raynel.alkemyproject.view.loginActivity.login.LoginViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private lateinit var viewModel: SignUpViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

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
            navigateToLogIn().observe(viewLifecycleOwner, Observer { navigate ->
                navigate?.let {
                    findNavController()
                        .navigate(R.id.action_SignFragment_to_LoginFragment)
                    doneNavigateToLogIn()
                }
            })

            singUpSuccess().observe(viewLifecycleOwner, Observer {
                val activity = requireActivity() as LoginActivity
                activity.onCreateUser(
                    binding.textInputdEmail.text.toString(),
                    binding.textInputPassword.text.toString(),
                    binding.textInputdName.text.toString()
                )
            })
        }
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this)
            .get(SignUpViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}