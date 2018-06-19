package com.b00sti.travelbucketlist.api

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger

/**
 * Created by b00sti on 08.02.2018
 */
object RxFirebaseDatabase {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseMain: DatabaseReference
    private lateinit var databaseCountries: DatabaseReference

    private const val MAIN = "main"

    fun setUpDatabase() {
        database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        database.setLogLevel(Logger.Level.DEBUG)
        databaseMain = database.reference.child(MAIN)
        databaseMain.keepSynced(true)
    }

/*    fun <T> call(observable: Observable<Response<T>>,
                 onSuccess: (T?) -> Unit,
                 onError: (Throwable, ERROR) -> Unit = DEFAULT_ERROR_HANDLER,
                 doOnSubscribe: (Disposable) -> Unit = {},
                 doOnTerminate: Action = Action { }): Disposable {
        var tokenRefreshTried = false
        return observable
                .compose(RxUtils.applyObservableSchedulers())
                .doOnSubscribe(doOnSubscribe)
                .doOnTerminate(doOnTerminate)
                .subscribeBy(
                        onNext = {
                            if (it.isSuccessful && it.body() != null) {
                                tokenRefreshTried = false
                                onSuccess(it.body())
                            } else {
                                when (it.code()) {
                                    401 -> {
                                        onError(Throwable(it.errorBody().toString()), ERROR.UNAUTHORIZED)
                                    }
                                    403 -> {
                                        onError(Throwable(it.errorBody().toString()), ERROR.TOKEN_EXPIRED)
                                        if (tokenRefreshTried) {
                                            return@subscribeBy
                                        }
                                        call(network.loginUser(AuthUser(Session.getRefreshToken())),
                                                onSuccess = {
                                                    Session.set(it)
                                                    call(observable, onSuccess, onError)
                                                },
                                                onError = { throwable, _ ->
                                                    tokenRefreshTried = true
                                                    onError(throwable, ERROR.REFRESH_TOKEN_FAILED)
                                                    App.instance.getRxBus().send(LogoutEvent())
                                                }
                                        )
                                    }
                                    404 -> {
                                        onError(Throwable(it.errorBody().toString()), ERROR.NOT_FOUND)
                                    }
                                    410 -> {
                                        onError(Throwable(it.errorBody().toString()), ERROR.TOKEN_EXPIRED)
                                        if (tokenRefreshTried) {
                                            return@subscribeBy
                                        }
                                        call(network.loginUser(AuthUser(Session.getRefreshToken())),
                                                onSuccess = {
                                                    Session.set(it)
                                                    call(observable, onSuccess, onError)
                                                },
                                                onError = { throwable, _ ->
                                                    tokenRefreshTried = true
                                                    onError(throwable, ERROR.REFRESH_TOKEN_FAILED)
                                                    App.instance.getRxBus().send(LogoutEvent())
                                                }
                                        )
                                    }
                                    else -> {
                                        onError(Throwable(it.errorBody().toString()), ERROR.UNKNOWN)
                                    }
                                }
                            }
                        },
                        onError = {
                            Timber.e(it.toString())
                            onError(it, ERROR.UNKNOWN)
                        }
                )
    }*/

}