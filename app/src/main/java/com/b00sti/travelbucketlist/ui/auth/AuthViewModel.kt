package com.b00sti.travelbucketlist.ui.auth

import com.b00sti.travelbucketlist.base.BaseViewModel

/**
 * Created by b00sti on 13.02.2018
 */
class AuthViewModel : BaseViewModel<AuthNavigator>() {

    fun onBackArrowClick() {
        getNavigator().onBackClick()
    }
/*
    fun handleFacebookResult(result: LoginResult) {
        getCompositeDisposable().add(RxFirebaseAuth.loginAndGetDataFromFacebook(result.accessToken)
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
        getCompositeDisposable().add(NetworkManager.loginUser(token, AuthRequest(user, facebookId, friends))
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