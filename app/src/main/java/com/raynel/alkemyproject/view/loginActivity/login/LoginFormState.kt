package com.raynel.alkemyproject.view.loginActivity.login

data class LoginFormState (
    var emailError: Boolean? = null,
    var passWordError: Boolean? = null,
    var isAllValid: Boolean? = null,
)