package com.raynel.alkemyproject.view.principalActivity.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raynel.alkemyproject.util.GenericViewModel

class UserViewModel : GenericViewModel() {

    val _navigateToDialog: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun navigateToDialog(): LiveData<Boolean?> = _navigateToDialog

    val _signOut: MutableLiveData<Boolean?> by lazy { MutableLiveData() }
    fun signOut(): LiveData<Boolean?> = _signOut

    fun doNavigateToDialog(){
        _navigateToDialog.value = true
    }

    fun doneNavigateToDialog(){
        _navigateToDialog.value = null
    }

    fun onLogOut(){
        _signOut.value = true
    }

    fun doneLogOut(){
        _signOut.value = null
    }
}