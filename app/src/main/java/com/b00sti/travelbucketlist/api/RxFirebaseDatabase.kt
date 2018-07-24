package com.b00sti.travelbucketlist.api

import com.google.firebase.database.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import kotlin.collections.ArrayList

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
    data class BucketItem(val name: String = "", val photoUrl: String = "", val type: Int = 1, val desc: String = "")

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

    fun getBucketList(bucketList: BucketList): Single<ArrayList<BucketItem>> = Single.create { emitter ->
        databaseAllLists.child("621895611").child("all_items").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                if (error != null) emitter.onError(error.toException())
                else emitter.onError(NullPointerException("Canceled with null value!"))
            }

            override fun onDataChange(data: DataSnapshot?) {
                val items = ArrayList<BucketItem>()
                when {
                    data == null || data.childrenCount == 0L -> emitter.onSuccess(items)
                    else                                     -> {

                        val obsList: MutableList<Single<BucketItem>> = mutableListOf()
                        for (entry in data.children) {
                            obsList.add(getBucketItem(entry.key))
                        }
                        Single.zip<BucketItem, ArrayList<BucketItem>>(obsList, { t: Array<out Any> ->
                            val list = arrayListOf<BucketItem>()
                            t.forEach { any -> list.add(any as BucketItem) }
                            list
                        })
                                .subscribeBy { t -> emitter.onSuccess(t) }
                    }
                }
            }
        })
    }

    fun getBucketItem(key: String): Single<BucketItem> = Single.create { emitter ->
        databasePublicItems
                .child("all_items")
                .child(key)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        if (error != null) emitter.onError(error.toException())
                        else emitter.onError(NullPointerException("Canceled with null value!"))
                    }

                    override fun onDataChange(data: DataSnapshot?) {
                        val item: BucketItem = BucketItem("", "", 1, "")
                        when {
                            data == null -> emitter.onSuccess(item)
                            else         -> {
                                /*for (entry in data.children) {
                                    try {
                                        // entry.key
                                        // entry.getValue(BucketItem::class.java)?.let { items.add(it) }
                                    } catch (e: DatabaseException) {
                                        e.printStackTrace()
                                    }
                                }*/
                                data.getValue(BucketItem::class.java)?.let {
                                    emitter.onSuccess(it)
                                }
                            }
                        }
                    }
                })
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