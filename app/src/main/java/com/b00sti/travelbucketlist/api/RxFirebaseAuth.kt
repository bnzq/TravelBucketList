package com.b00sti.travelbucketlist.api

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by b00sti on 08.02.2018
 */
object RxFirebaseAuth {
    private val auth = FirebaseAuth.getInstance()

/*    fun getUserFromFirebase(): Observable<FirebaseUser> {
        return Observable.create { emitter ->
            val user = auth.currentUser
            if (user == null) emitter.onError(handleAuthException(NullPointerException("No selectedUser returned from Firebase")))
            else emitter.onNext(user)
            emitter.onComplete()
        }
    }*/
}