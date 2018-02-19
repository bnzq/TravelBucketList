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
import com.google.firebase.auth.FirebaseUser
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
                R.id.tietUsernameRegister -> Validation.validateUsername(name.get(), nameValid)
                R.id.tietPasswordRegister -> Validation.validatePassword(password, passwordValid)
                R.id.tietPasswordConfirmRegister -> Validation.validatePasswordConfirm(password, passwordConfirm, passwordConfirmValid)
            }
    }

    fun afterChangeEmpty() {
        if (email.get().isEmpty()) Validation.validateEmail(email, emailValid)
        if (name.get().isEmpty()) Validation.validateUsername(name.get(), nameValid)
        if (password.get().isEmpty()) Validation.validatePassword(password, passwordValid)
        if (passwordConfirm.get().isEmpty()) Validation.validatePasswordConfirm(password, passwordConfirm, passwordConfirmValid)
    }

    fun onRegisterClick() {
        Timber.d("register ")
        if (Validation.validateEmail(email, emailValid)
                && Validation.validateUsername(name.get(), nameValid)
                && Validation.validatePassword(password, passwordValid)
                && Validation.validatePasswordConfirm(password, passwordConfirm, passwordConfirmValid)) {

            getCompositeDisposable().add(RxFirebaseAuth.registerAndGetToken(email.get(), password.get(), name.get())
                    .compose(RxUtils.applyObservableSchedulers())
                    .doOnSubscribe { getNavigator().onLoading(true) }
                    .subscribeBy(
                            onNext = {
                                Timber.d("register next")
//                                getNavigator().openMainActivity()
                                getNavigator().openMainActivity()
                                //addUser(it.third, it.second)
                            },
                            onError = {
                                Timber.d("register error")
                                getNavigator().showError(it.localizedMessage)
                            }
                    ))
        } else {
            val builder = StringBuilder()
            builder.append(ResUtils.getString(R.string.invalid_credentials)).append(":\n\n")
            if (!emailValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_email)).append("\n")
            if (!nameValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_username)).append("\n")
            if (!passwordValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_password)).append("\n")
            if (!passwordConfirmValid.get()) builder.append(" - ").append(ResUtils.getString(R.string.invalid_confirm)).append("\n")
            getNavigator().showError(builder.toString(), "Validation")
        }
    }

    private fun addUser(token: String, user: FirebaseUser) {
        Timber.w("add User top")
/*        getCompositeDisposable().add(NetworkManager.loginUser(token, AuthRequest(user))
                .compose(RxUtils.applyObservableSchedulers())
                .doOnTerminate { getNavigator().onLoading(false) }
                .subscribeBy(
                        onNext = {
                            if (it.isSuccessful) getNavigator().openMainActivity()
                            else {
                                getNavigator().showError(it.message(), it.code().toString())
                            }
                        },
                        onError = {
                            getNavigator().showError("Failed")
                        }
                ))*/
    }

    fun onFacebookClick() = getNavigator().registerFacebook()

    fun onLoginClick() = getNavigator().openLoginFragment()

}