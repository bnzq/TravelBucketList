package com.b00sti.travelbucketlist.api

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import io.reactivex.Completable
import java.util.*

/**
 * Created by b00sti on 08.02.2018
 */
object RxFirebaseDatabase {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseMain: DatabaseReference
    private lateinit var databaseAllLists: DatabaseReference
    private lateinit var databasePublicLists: DatabaseReference
    private lateinit var databasePublicItems: DatabaseReference

    private const val MAIN = "main"
    private const val LISTS = "all_lists"
    private const val PUBLIC_LISTS = "public_lists"
    private const val PUBLIC_ITEMS = "public_items"

    fun setUpDatabase() {
        database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        database.setLogLevel(Logger.Level.DEBUG)
        databaseMain = database.reference.child(MAIN)
        databaseAllLists = database.reference.child(LISTS)
        databasePublicLists = database.reference.child(PUBLIC_LISTS)
        databasePublicItems = database.reference.child(PUBLIC_ITEMS)
        databaseMain.keepSynced(true)
    }

    data class BucketList(val name: String, val isPublic: Boolean, val userId: String)
    data class BucketItem(val name: String, val photoUrl: String, val type: Int, val desc: String)

    fun addNewBucketList(bucketList: BucketList): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        map[bucketList.hashCode().toString()] = bucketList
        databaseAllLists.updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    fun addNewItem(bucketItem: BucketItem): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        map[bucketItem.hashCode().toString()] = bucketItem
        databasePublicItems.child("all_items").updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    fun assignToBucketList(bucketItem: BucketItem, bucketList: BucketList): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        map[bucketItem.hashCode().toString()] = bucketItem.hashCode().toString()
        databaseAllLists.child(bucketList.hashCode().toString()).child("all_items").updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

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