package com.raynel.alkemyproject.view.loginActivity.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raynel.alkemyproject.isValidEmail
import com.raynel.alkemyproject.isValidPassword
import com.raynel.alkemyproject.util.GenericViewModel

class LoginViewModel: GenericViewModel() {

    private val _navigateToSignUp: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun navigateToSignUp(): LiveData<Boolean?> = _navigateToSignUp

    private val _logInFormState: MutableLiveData<LoginFormState?> by lazy { MutableLiveData() }
    fun logInFormState(): LiveData<LoginFormState?> = _logInFormState

    fun doNavigateToSignUp(){
        _navigateToSignUp.value = true
    }

    fun doneNavigateToSignUp(){
        _navigateToSignUp.value = null
    }

    fun onSignInPressed(email: String, password: String){
        val loginFormState = LoginFormState()

        val isValidEmail = isValidEmail(email)
        val isValidPassword = isValidPassword(password)

        if(!isValidEmail){
            loginFormState.emailError = true
        }

        if(!isValidPassword){
            loginFormState.passWordError = true
        }

        if(isValidEmail && isValidPassword){
            loginFormState.isAllValid = true
        }

        _logInFormState.value =
            loginFormState
    }

    fun doneSignInPressed(){
        _logInFormState.value = null
    }

}