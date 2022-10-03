package com.raynel.alkemyproject.view.loginActivity.signup

class SignUpFormState(
    var nameError: Boolean? = null,
    var emailError: Boolean? = null,
    var passwordError: Boolean? = null,
    var confirmationPasswordNotSame: Boolean? = null,
    var isAllValid: Boolean? = null
)