package com.raynel.alkemyproject.view.loginActivity.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raynel.alkemyproject.isValidEmail
import com.raynel.alkemyproject.isValidPassword
import com.raynel.alkemyproject.util.GenericViewModel

class SignUpViewModel: GenericViewModel() {

    private val _navigateToLogIn: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun navigateToLogIn(): LiveData<Boolean?> = _navigateToLogIn

    private val _singUpFormState: MutableLiveData<SignUpFormState?> by lazy { MutableLiveData() }
    fun singUpFormState(): LiveData<SignUpFormState?> = _singUpFormState

    fun doNavigateToLogIn(){
        _navigateToLogIn.value = true
    }

    fun doneNavigateToLogIn(){
        _navigateToLogIn.value = null
    }

    fun onSignUp(name: String, email: String, password: String, confirmationPassword: String
    ){

        val formState =  SignUpFormState()

        val isEmailValid = isValidEmail(email)
        val isPasswordValid = isValidPassword(password)
        val isNameValid = name.isNotEmpty()
        val passwordsAreSame = (password == confirmationPassword)

        if(!isNameValid){
            formState.nameError = true
        }

        if(!isEmailValid){
            formState.emailError = true
        }

        if(!isPasswordValid){
            formState.passwordError = true
        }

        if(!passwordsAreSame){
            formState.confirmationPasswordNotSame = true
        }

        if(isNameValid && isEmailValid && isPasswordValid && passwordsAreSame){
            formState.isAllValid = true
        }

        _singUpFormState.value = formState
    }

    fun doneSignUp(){
        _singUpFormState.value = null
    }
}