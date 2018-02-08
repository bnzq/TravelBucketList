package com.b00sti.travelbucketlist.utils

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import java.util.regex.Pattern


/**
 * @author Michał Palczyński
 * @since 14.12.2017.
 */
object Validation {

    fun validateEmail(email: ObservableField<String>, emailValid: ObservableBoolean): Boolean {
        val valid = Pattern.compile(AppConstants.EMAIL_REGEX).matcher(email.get()).matches()
        emailValid.set(valid)
        emailValid.notifyChange()
        return valid
    }

    fun validatePassword(password: ObservableField<String>, passwordValid: ObservableBoolean): Boolean {
        val valid = validate(AppConstants.PASSWORD_REGEX, password.get())
        passwordValid.set(valid)
        passwordValid.notifyChange()
        return valid
    }

    fun validatePasswordConfirm(password: ObservableField<String>, passwordConfirm: ObservableField<String>, passwordConfirmValid: ObservableBoolean): Boolean {
        val valid = (password.get() == passwordConfirm.get()) && validate(AppConstants.PASSWORD_REGEX, password.get())
        passwordConfirmValid.set(valid)
        passwordConfirmValid.notifyChange()
        return valid
    }

    fun validateOther(text: String, textValid: ObservableBoolean): Boolean {
        val valid = validate(AppConstants.ABOUT_ME_REGEX, text)
        textValid.set(valid)
        textValid.notifyChange()
        return valid
    }

    fun validateUsername(name: String, nameValid: ObservableBoolean): Boolean {
        val valid = validate(AppConstants.USERNAME_REGEX, name)
        nameValid.set(valid)
        nameValid.notifyChange()
        return valid
    }

    private fun validate(patter: String, string: String): Boolean {
        return Pattern.compile(patter).matcher(string).matches()
    }

}