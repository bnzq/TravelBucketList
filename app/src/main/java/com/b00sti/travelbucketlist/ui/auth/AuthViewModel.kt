package com.b00sti.travelbucketlist.ui.auth

import com.b00sti.travelbucketlist.api.RxFbAuth
import com.b00sti.travelbucketlist.base.BaseVM
import com.b00sti.travelbucketlist.utils.RxUtils
import com.facebook.login.LoginResult
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 * Created by b00sti on 13.02.2018
 */
class AuthViewModel : BaseVM<AuthNavigator>() {

    fun onBackArrowClick() {
        getNavigator().onBackClick()
    }


    fun handleFacebookResult(result: LoginResult) {
        getDisposables().add(RxFbAuth.loginFacebook(result.accessToken)
                .compose(RxUtils.applyObservableSchedulers())
                .doOnSubscribe { getNavigator().onStartLoading() }
                .subscribeBy(
                        onNext = {
                            getNavigator().openMainActivity()
                        },
                        onComplete = {
                            Timber.d("Complete")
                        },
                        onError = {
                            it.printStackTrace()
                            getNavigator().showErrorDialog("Something went wrong, try again!")
                        }
                ))
    }

/*
    fun handleFacebookResult(result: LoginResult) {
        getDisposables().add(RxFbAuth.loginAndGetDataFromFacebook(result.accessToken)
                .compose(RxUtils.applyObservableSchedulers())
                .doOnSubscribe { getNavigator().onLoading(true) }
                .subscribeBy(
                        onNext = { addUser(it.third, it.first, result.accessToken.userId, it.second.data) },
                        onError = {
                            it.printStackTrace()
                            getNavigator().showError("Something went wrong, try again!")
                        }
                ))
    }*/


/*    private fun addUser(token: String, user: FirebaseUser, facebookId: String? = null, friends: ArrayList<FacebookFriend>?) {
        getDisposables().add(NetworkManager.loginUser(token, AuthRequest(user, facebookId, friends))
                .compose(RxUtils.applyObservableSchedulers())
                .doAfterTerminate { getNavigator().onLoading(false) }
                .subscribeBy(
                        onNext = { getNavigator().openMainActivity() },
                        onError = {
                            it.printStackTrace()
                            getNavigator().showError(it.localizedMessage)
                        }
                ))
    }*/

}