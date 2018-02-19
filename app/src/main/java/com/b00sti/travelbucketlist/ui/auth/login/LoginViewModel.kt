package com.b00sti.travelbucketlist.ui.auth.login

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.ResUtils
import com.b00sti.travelbucketlist.utils.Validation.validateEmail
import com.b00sti.travelbucketlist.utils.Validation.validatePassword

/**
 * Created by b00sti on 15.02.2018
 */
class LoginViewModel : BaseViewModel<LoginNavigator>() {
    val email = ObservableField<String>("")
    val emailValid = ObservableBoolean(false)
    val password = ObservableField<String>("")
    val passwordValid = ObservableBoolean(false)

    val focusChange = View.OnFocusChangeListener { view, focus ->
        if (!focus)
            when (view.id) {
                R.id.tietEmailLogin -> validateEmail(email, emailValid)
                R.id.tietPasswordLogin -> validatePassword(password, passwordValid)
            }
    }

    fun afterChangeEmpty() {
        if (email.get().isEmpty()) validateEmail(email, emailValid)
        if (password.get().isEmpty()) validatePassword(password, passwordValid)
    }

    fun onLoginClick() {
        if (validateEmail(email, emailValid) && validatePassword(password, passwordValid)) {
/*            getCompositeDisposable()
                    .add(RxFirebaseAuth.loginCredentials(email.get(), password.get())
                            .compose(RxUtils.applyObservableSchedulers())
                            .doOnSubscribe { getNavigator().onLoading(true) }
                            .doAfterTerminate { getNavigator().onLoading(false) }
                            .subscribeBy(
                                    onNext = { getNavigator().openMainActivity() },
                                    onError = { getNavigator().showError(it.localizedMessage) })
                    )*/
        } else {
            val builder = StringBuilder()
            builder.append(ResUtils.getString(R.string.invalid_credentials)).append(":\n\n")
            if (!emailValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_email)).append("\n")
            if (!passwordValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_password)).append("\n")
            getNavigator().showError(builder.toString(), "Validation")

        }
    }

    fun onRegisterClick() = getNavigator().openRegisterFragment()

    fun onFacebookClick() = getNavigator().loginFacebook()

    fun onForgotClick() = getNavigator().openForgotFragment()


}