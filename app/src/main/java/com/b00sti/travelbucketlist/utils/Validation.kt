package com.b00sti.travelbucketlist.utils

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import java.util.regex.Pattern

object Validation {

    fun validateEmail(email: ObservableField<String>, emailValid: ObservableBoolean): Boolean {
        val valid = Pattern.compile(AppConstants.EMAIL_REGEX).matcher(email.get()).matches()
        emailValid.set(valid)
        return valid
    }

    fun validatePassword(password: ObservableField<String>, passwordValid: ObservableBoolean): Boolean {
        val valid = validate(AppConstants.PASSWORD_REGEX, password.getOrEmpty() )
        passwordValid.set(valid)
        return valid
    }

    fun validatePasswordConfirm(password: ObservableField<String>, passwordConfirm: ObservableField<String>, passwordConfirmValid: ObservableBoolean): Boolean {
        val valid = (password.get() == passwordConfirm.get()) && validate(AppConstants.PASSWORD_REGEX, password.getOrEmpty())
        passwordConfirmValid.set(valid)
        return valid
    }

    fun validateOther(text: String, textValid: ObservableBoolean): Boolean {
        val valid = validate(AppConstants.ABOUT_ME_REGEX, text)
        textValid.set(valid)
        return valid
    }

    fun validateUsername(name: String, nameValid: ObservableBoolean): Boolean {
        val valid = validate(AppConstants.USERNAME_REGEX, name)
        nameValid.set(valid)
        return valid
    }

    private fun validate(patter: String, string: String): Boolean {
        return Pattern.compile(patter).matcher(string).matches()
    }

}