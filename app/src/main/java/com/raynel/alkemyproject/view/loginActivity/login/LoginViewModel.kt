package com.raynel.alkemyproject.view.loginActivity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raynel.alkemyproject.util.GenericViewModel

class LoginViewModel: GenericViewModel() {

    private val _navigateToSignUp: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun navigateToSignUp(): LiveData<Boolean?> = _navigateToSignUp

    private val _logInSuccess: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun logInSuccess(): LiveData<Boolean?> = _logInSuccess

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