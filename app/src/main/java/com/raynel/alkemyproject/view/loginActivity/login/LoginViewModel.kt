package com.raynel.alkemyproject.view.loginActivity.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raynel.alkemyproject.util.GenericViewModel
import java.util.regex.Pattern

class LoginViewModel: GenericViewModel() {

    private val _navigateToSignUp: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun navigateToSignUp(): LiveData<Boolean?> = _navigateToSignUp

    private val _logInSuccess: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun logInSuccess(): LiveData<Boolean?> = _logInSuccess

    private val _loginFormState: MutableLiveData<LoginFormState?> by lazy { MutableLiveData() }
    fun loginFormState(): LiveData<LoginFormState?> = _loginFormState

    fun onTextFieldsChange(email: String, password: String) {
        if(!isValidEmail(email)){
            _loginFormState.value =
                LoginFormState.EMAIL_ERROR
        }else if(!isValidPassword(password)){
            _loginFormState.value =
                LoginFormState.PASSWORD_ERROR
        }else{
            _loginFormState.value =
                LoginFormState.IS_ALL_VALID
        }
    }

    private fun isValidEmail(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun isValidPassword(password: String): Boolean{
        return password.length > 7
    }

    fun doNavigateToSignUp(){
        _navigateToSignUp.value = true
    }

    fun doneNavigateToSignUp(){
        _navigateToSignUp.value = null
    }

    fun onLogIn(){
        _logInSuccess.value = true
    }

    fun doneLogIn(){
        _logInSuccess.value = null
    }

}