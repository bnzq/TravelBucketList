package com.b00sti.travelbucketlist.ui.auth.register

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.api.RxFirebaseAuth
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.ResUtils
import com.b00sti.travelbucketlist.utils.RxUtils
import com.b00sti.travelbucketlist.utils.Validation
import com.b00sti.travelbucketlist.utils.getOrEmpty
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class RegisterViewModel : BaseViewModel<RegisterNavigator>() {

    val name = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")
    val passwordConfirm = ObservableField<String>("")
    val nameValid = ObservableBoolean(false)
    val emailValid = ObservableBoolean(false)
    val passwordValid = ObservableBoolean(false)
    val passwordConfirmValid = ObservableBoolean(false)

    val focusChange = View.OnFocusChangeListener { view, focus ->
        if (!focus)
            when (view.id) {
                R.id.tietEmailRegister -> Validation.validateEmail(email, emailValid)
                R.id.tietUsernameRegister -> Validation.validateUsername(name.getOrEmpty(), nameValid)
                R.id.tietPasswordRegister -> Validation.validatePassword(password, passwordValid)
                R.id.tietPasswordConfirmRegister -> Validation.validatePasswordConfirm(password, passwordConfirm, passwordConfirmValid)
            }
    }

    fun afterChangeEmpty() {
        if (email.getOrEmpty().isEmpty()) Validation.validateEmail(email, emailValid)
        if (name.getOrEmpty().isEmpty()) Validation.validateUsername(name.getOrEmpty(), nameValid)
        if (password.getOrEmpty().isEmpty()) Validation.validatePassword(password, passwordValid)
        if (passwordConfirm.getOrEmpty().isEmpty()) Validation.validatePasswordConfirm(password, passwordConfirm, passwordConfirmValid)
    }

    fun onRegisterClick() {
        Timber.d("register ")
        if (Validation.validateEmail(email, emailValid)
                && Validation.validateUsername(name.getOrEmpty(), nameValid)
                && Validation.validatePassword(password, passwordValid)
                && Validation.validatePasswordConfirm(password, passwordConfirm, passwordConfirmValid)) {

            getCompositeDisposable().add(RxFirebaseAuth.registerAndGetToken(email.getOrEmpty(), password.getOrEmpty(), name.getOrEmpty())
                    .compose(RxUtils.applyObservableSchedulers())
                    .doOnSubscribe { getNavigator().onLoading(true) }
                    .subscribeBy(
                            onNext = {
                                Timber.d("register next")
                                getNavigator().openMainActivity()
                                //addUser(it.third, it.second)
                            },
                            onError = {
                                Timber.d("register error")
                                getNavigator().showErrorDialog(it.localizedMessage)
                            }
                    ))
        } else {
            val builder = StringBuilder()
            builder.append(ResUtils.getString(R.string.invalid_credentials)).append(":\n\n")
            if (!emailValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_email)).append("\n")
            if (!nameValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_username)).append("\n")
            if (!passwordValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_password)).append("\n")
            if (!passwordConfirmValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_confirm)).append("\n")
            getNavigator().showErrorDialog(builder.toString(), "Validation")
        }
    }

    fun onFacebookClick() = getNavigator().registerFacebook()

    fun onLoginClick() = getNavigator().openLoginFragment()

}