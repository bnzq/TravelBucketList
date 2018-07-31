package com.b00sti.travelbucketlist.api

import com.b00sti.travelbucketlist.model.Bucket
import com.b00sti.travelbucketlist.utils.copyAndClear
import com.b00sti.travelbucketlist.utils.toArrayList
import com.google.firebase.database.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

/**
 * Created by b00sti on 08.02.2018
 */
object RxFbDatabase {

    //region Database contract and references
    private lateinit var db: FirebaseDatabase

    private const val MAIN = "main"
    private lateinit var dbMain: DatabaseReference

    private const val ALL_BUCKET_LISTS = "all_bucket_lists"
    private lateinit var dbAllBucketLists: DatabaseReference

    private const val PUBLIC_ITEMS = "all_to_dos"
    private lateinit var dbAllToDos: DatabaseReference

    private const val USERS = "all_users"
    private lateinit var dbUsers: DatabaseReference

    private const val USERS_TO_DO = "to_dos"
    private const val USERS_LISTS = "lists"

    //endregion

    fun setUpDatabase() {
        db = FirebaseDatabase.getInstance()
        db.setPersistenceEnabled(true)
        db.setLogLevel(Logger.Level.DEBUG)
        dbMain = db.reference.child(MAIN)
        dbAllBucketLists = dbMain.child(ALL_BUCKET_LISTS)
        dbAllToDos = dbMain.child(PUBLIC_ITEMS)
        dbUsers = dbMain.child(USERS)
        dbMain.keepSynced(true)
    }

    //region Database functions
    fun addNewBucketList(bucketList: Bucket.List): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        bucketList.id = dbAllBucketLists.push().key
        map[bucketList.id] = bucketList
        dbAllBucketLists.updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    fun copyBucketList(bucketList: Bucket.List): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        map[bucketList.id] = bucketList
        dbUsers.child(RxFbAuth.getUserId()).child(USERS_LISTS).updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                emitter.onComplete()
                copyBucketListTodos(bucketList)
            } else task.exception?.let { emitter.onError(it) }
        })
    })

    private fun copyBucketListTodos(bucketList: Bucket.List) {
        getBucketList(bucketList).subscribeBy { list ->
            list.forEach {
                dbUsers.child(RxFbAuth.getUserId()).child(USERS_TO_DO).updateChildren(mapOf(Pair(it.id, it)))
            }
        }
    }

    fun addNewBucketToDo(bucketToDo: Bucket.ToDo): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        bucketToDo.id = dbAllToDos.push().key
        map[bucketToDo.id] = bucketToDo
        dbAllToDos.updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    fun assignToDoToBucketList(bucketToDo: Bucket.ToDo, bucketList: Bucket.List): Completable = Completable.create({ emitter ->
        val map = WeakHashMap<String, Any>()
        map[bucketToDo.id] = bucketToDo.id
        dbAllBucketLists.child(bucketList.id).child("to_dos").updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    fun getBucketList(bucketList: Bucket.List): Observable<ArrayList<Bucket.ToDo>> = Observable.create { emitter ->
        dbAllBucketLists.child(bucketList.id).child("to_dos").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) = handleDatabaseErrors(error, emitter)
            override fun onDataChange(data: DataSnapshot?) {
                when {
                    isEmpty(data) -> emitter.onNext(arrayListOf())
                    else          -> getPaginedItems(data, emitter, ::getBucketItem)
                }
            }
        })
    }

    fun getMyBucketList(bucketList: Bucket.List): Observable<ArrayList<Bucket.ToDo>> = Observable.create { emitter ->
        dbUsers.child(RxFbAuth.getUserId()).child(USERS_LISTS).child(bucketList.id).child("to_dos").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) = handleDatabaseErrors(error, emitter)
            override fun onDataChange(data: DataSnapshot?) {
                when {
                    isEmpty(data) -> emitter.onNext(arrayListOf())
                    else          -> getPaginedItems(data, emitter, ::getMyBucketItem)
                }
            }
        })
    }

    fun getAllMyBucketLists(): Observable<ArrayList<Bucket.List>> = Observable.create { emitter ->
        dbUsers.child(RxFbAuth.getUserId()).child(USERS_LISTS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) = handleDatabaseErrors(error, emitter)
            override fun onDataChange(data: DataSnapshot?) {
                when {
                    isEmpty(data) -> emitter.onNext(arrayListOf())
                    else          -> {
                        val items = arrayListOf<Bucket.List>()
                        for (entry in data!!.children) {
                            try {
                                entry.getValue(Bucket.List::class.java)?.let {
                                    items.add(it)
                                }
                            } catch (e: DatabaseException) {
                                e.printStackTrace()
                            }
                        }
                        emitter.onNext(items)
                    }
                }
            }
        })
    }

    fun getAllPublicBucketLists(): Observable<ArrayList<Bucket.List>> = Observable.create { emitter ->
        dbAllBucketLists.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) = handleDatabaseErrors(error, emitter)
            override fun onDataChange(data: DataSnapshot?) {
                when {
                    isEmpty(data) -> emitter.onNext(arrayListOf())
                    else          -> {
                        val items = arrayListOf<Bucket.List>()
                        for (entry in data!!.children) {
                            try {
                                entry.getValue(Bucket.List::class.java)?.let {
                                    items.add(it)
                                }
                            } catch (e: DatabaseException) {
                                e.printStackTrace()
                            }
                        }
                        emitter.onNext(items)
                    }
                }
            }
        })
    }

    private fun getBucketItem(key: String): Single<Bucket.ToDo> = Single.create { emitter ->
        dbAllToDos
                .child(key)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        if (error != null) emitter.onError(error.toException())
                        else emitter.onError(NullPointerException("Canceled with null value!"))
                    }

                    override fun onDataChange(data: DataSnapshot?) {
                        val toDo: Bucket.ToDo = Bucket.ToDo("", "", 1, "")
                        when {
                            data == null -> emitter.onSuccess(toDo)
                            else         -> {
                                data.getValue(Bucket.ToDo::class.java)?.let {
                                    emitter.onSuccess(it)
                                }
                            }
                        }
                    }
                })
    }

    private fun getMyBucketItem(key: String): Single<Bucket.ToDo> = Single.create { emitter ->
        dbUsers
                .child(RxFbAuth.getUserId())
                .child(USERS_TO_DO)
                .child(key)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        if (error != null) emitter.onError(error.toException())
                        else emitter.onError(NullPointerException("Canceled with null value!"))
                    }

                    override fun onDataChange(data: DataSnapshot?) {
                        val toDo: Bucket.ToDo = Bucket.ToDo("", "", 1, "")
                        when {
                            data == null -> emitter.onSuccess(toDo)
                            else         -> {
                                data.getValue(Bucket.ToDo::class.java)?.let {
                                    emitter.onSuccess(it)
                                }
                            }
                        }
                    }
                })
    }

    //endregion

    //region Utils
    private fun <T> getPaginedItems(data: DataSnapshot?, emitter: ObservableEmitter<ArrayList<T>>, fetchItem: (String) -> Single<T>, PAGINATION_SIZE: Int = 20) {
        val listOfQueries: MutableList<Single<T>> = mutableListOf()
        var counter = 1
        data!!.children.forEach { entry ->
            listOfQueries.add(fetchItem(entry.key))
            counter++
            if (counter > PAGINATION_SIZE) {
                counter = 1
                Single.zip<T, ArrayList<T>>(listOfQueries.copyAndClear(), { it.toArrayList() })
                        .subscribeBy { emitter.onNext(it) }
            }
        }
    }

    private fun isEmpty(data: DataSnapshot?): Boolean = data == null || data.childrenCount == 0L

    private fun <T> handleDatabaseErrors(error: DatabaseError?, emitter: ObservableEmitter<ArrayList<T>>) {
        if (error != null) emitter.onError(error.toException())
        else emitter.onError(NullPointerException("Canceled with null value!"))
    }
    //endregion

}
