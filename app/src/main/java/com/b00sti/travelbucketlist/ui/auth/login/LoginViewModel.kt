package com.b00sti.travelbucketlist.ui.auth.login

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.api.RxFirebaseAuth
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.ResUtils
import com.b00sti.travelbucketlist.utils.RxUtils
import com.b00sti.travelbucketlist.utils.Validation.validateEmail
import com.b00sti.travelbucketlist.utils.Validation.validatePassword
import com.b00sti.travelbucketlist.utils.getOrEmpty
import io.reactivex.rxkotlin.subscribeBy

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
        if (email.getOrEmpty().isEmpty()) validateEmail(email, emailValid)
        if (password.getOrEmpty().isEmpty()) validatePassword(password, passwordValid)
    }

    fun onLoginClick() {
        if (validateEmail(email, emailValid) && validatePassword(password, passwordValid)) {
            getDisposables()
                    .add(RxFirebaseAuth.loginCredentials(email.getOrEmpty(), password.getOrEmpty())
                            .compose(RxUtils.applyObservableSchedulers())
                            .doOnSubscribe { getNavigator().onStartLoading() }
                            .doAfterTerminate { getNavigator().onFinishLoading() }
                            .subscribeBy(
                                    onNext = { getNavigator().openMainActivity() },
                                    onError = { getNavigator().showToast(it.localizedMessage) })
                    )
        } else {
            val builder = StringBuilder()
            builder.append(ResUtils.getString(R.string.invalid_credentials)).append(":\n\n")
            if (!emailValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_email)).append("\n")
            if (!passwordValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_password)).append("\n")
            getNavigator().showErrorDialog(builder.toString(), "Validation")

        }
    }

    fun onRegisterClick() = getNavigator().openRegisterFragment()

    fun onFacebookClick() = getNavigator().loginFacebook()

    fun onForgotClick() = getNavigator().openForgotFragment()


}