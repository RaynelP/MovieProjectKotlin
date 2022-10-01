package com.raynel.alkemyproject.view.loginActivity.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raynel.alkemyproject.util.GenericViewModel

class SignUpViewModel: GenericViewModel() {

    private val _navigateToLogIn: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun navigateToLogIn(): LiveData<Boolean?> = _navigateToLogIn

    private val _singUpSuccess: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun singUpSuccess(): LiveData<Boolean?> = _singUpSuccess

    fun doNavigateToLogIn(){
        _navigateToLogIn.value = true
    }

    fun doneNavigateToLogIn(){
        _navigateToLogIn.value = null
    }

    fun onSignUp(){
        _singUpSuccess.value = true
    }

    fun doneSignUp(){
        _singUpSuccess.value = null
    }
}