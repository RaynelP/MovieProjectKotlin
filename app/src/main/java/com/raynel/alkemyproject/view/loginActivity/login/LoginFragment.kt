package com.raynel.alkemyproject.view.loginActivity.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.FragmentLoginBinding
import com.raynel.alkemyproject.view.loginActivity.LoginActivity


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding
            .inflate(inflater, container, false)
        email = binding.textInputEmail
        password = binding.textInputPassword
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

            logInFormState().observe(viewLifecycleOwner, Observer { state ->

                state?.let {

                    it.emailError?.let {
                        email.error = "Email is invalid"
                    }

                    it.passWordError?.let {
                        password.error = "Password is short"
                    }

                    it.isAllValid?.let {
                        val activity = requireActivity() as  LoginActivity
                        activity.onLogInUser(
                            email.text.toString(),
                            password.text.toString()
                        )
                    }

                }

            })

        }
    }

    private fun configFieldsObservable(){
        val textWatcher= object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                //viewModel.onTextFieldsChange(
                //    email.text.toString(),
                //    password.text.toString()
                //)
            }

        }
        //email.addTextChangedListener(textWatcher)
        //password.addTextChangedListener(textWatcher)
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