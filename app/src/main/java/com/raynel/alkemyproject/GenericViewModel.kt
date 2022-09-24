package com.raynel.alkemyproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class GenericViewModel : ViewModel(){

    private val _retry: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    fun retry(): LiveData<Boolean> = _retry

    private val _error: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    fun error(): LiveData<Boolean> = _error

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    fun isLoading(): LiveData<Boolean> = _isLoading

    fun startRetry(){
        _retry.value = true
    }

    fun doneRetry(){
        _retry.value = null
    }

    fun onError(){
        _error.value = true
    }

    fun doneError(){
        _error.value = null
    }

    fun onLoanding() {
        _isLoading.value = true
    }

    fun doneLoanding() {
        _isLoading.value = false
    }

}