package com.b00sti.travelbucketlist.api

import android.net.Uri
import com.b00sti.travelbucketlist.utils.RxUtils
import com.b00sti.travelbucketlist.utils.SystemUtils
import com.facebook.AccessToken
import com.google.firebase.auth.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException

/**
 * Created by b00sti on 08.02.2018
 */
object RxFbAuth {
    private val auth = FirebaseAuth.getInstance()

    private fun handleAuthException(e: Exception = ConnectException()): Throwable {
        e.printStackTrace()
        return when (e) {
            is ConnectException                         -> IOException("No Internet Connection")
            is FirebaseAuthInvalidUserException         -> IOException("User does not exist!")
            is FirebaseAuthEmailException               -> IOException("Invalid Email!")
            is FirebaseAuthInvalidCredentialsException  -> IOException("Invalid email or password!")
            is FirebaseAuthUserCollisionException       -> when (e.errorCode) {
                "ERROR_USER_NOT_FOUND"                           -> IOException("User not found")
                "ERROR_USER_DISABLED"                            -> IOException("User disabled")
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> IOException("Logged in as other selectedUser")
                else                                             -> IOException(e.message)
            }
            is FirebaseAuthWeakPasswordException        -> IOException("Password is too weak.")
            is FirebaseAuthRecentLoginRequiredException -> IOException(e.message)
            is NullPointerException                     -> IOException(e.message)
            else                                        -> IOException("Error! Try again.")
        }
    }

    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    fun updateUser(name: String?, photo: String?): Observable<FirebaseUser> {
        return Observable.create { emitter ->
            val user = auth.currentUser
            if (user == null) {
                emitter.onError(handleAuthException(NullPointerException("No selectedUser returned from Firebase")))
                emitter.onComplete()
                return@create
            }

            if (SystemUtils.isConnected()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                name?.let { profileUpdates.setDisplayName(name) }
                photo?.let { profileUpdates.setPhotoUri(Uri.parse(it)) }
                user.updateProfile(profileUpdates.build()).addOnCompleteListener { task ->
                    if (task.isSuccessful) emitter.onNext(auth.currentUser ?: user)
                    else task.exception?.let { emitter.onError(handleAuthException(it)) }
                    emitter.onComplete()
                }
            } else {
                emitter.onError(handleAuthException())
                emitter.onComplete()
            }
        }
    }

    fun registerCredential(email: String, password: String): Observable<FirebaseUser> {
        return Observable.create { emitter ->
            if (SystemUtils.isConnected()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener({ task ->
                    if (task.isSuccessful) emitter.onNext(task.result.user)
                    else task.exception?.let { emitter.onError(handleAuthException(it)) }
                    emitter.onComplete()
                })
            } else {
                emitter.onError(handleAuthException())
                emitter.onComplete()
            }

        }
    }

    fun loginCredentials(email: String, password: String): Observable<FirebaseUser> {
        return Observable.create { emitter ->
            if (SystemUtils.isConnected()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener({ task ->
                    if (task.isSuccessful) emitter.onNext(task.result.user)
                    else task.exception?.let { emitter.onError(handleAuthException(it)) }
                    emitter.onComplete()
                })
            } else {
                emitter.onError(handleAuthException())
                emitter.onComplete()
            }
        }
    }

    fun loginFacebook(token: AccessToken): Observable<FirebaseUser> {
        return Observable.create { emitter ->
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential).addOnCompleteListener({ task ->
                if (task.isSuccessful) emitter.onNext(task.result.user)
                else task.exception?.let { emitter.onError(handleAuthException(it)) }
                emitter.onComplete()
            })

        }
    }

    fun isFacebook(): Boolean {
        return FirebaseAuth.getInstance().currentUser?.providerData?.any { it.providerId == "facebook.com" }
                ?: false
    }

    private fun reAuthenticate(user: FirebaseUser, password: String): Completable {
        return Completable.create { emitter ->
            val action: AuthCredential =
                    if (isFacebook()) FacebookAuthProvider.getCredential(AccessToken.getCurrentAccessToken().token)
                    else EmailAuthProvider.getCredential(user.email.toString(), password)
            user.reauthenticate(action).addOnCompleteListener({ task ->
                if (task.isSuccessful) emitter.onComplete()
                else emitter.onError(handleAuthException(task.exception ?: IOException()))
            })

        }
    }

    fun changeEmail(email: String, password: String): Completable {
        return Completable.create { emitter ->
            val user = auth.currentUser
            if (user == null) {
                emitter.onError(handleAuthException(NullPointerException("No selectedUser returned from Firebase")))
                emitter.onComplete()
                return@create
            }
            if (SystemUtils.isConnected()) {
                emitter.setDisposable(reAuthenticate(user, password).subscribeBy(
                        onComplete = {
                            user.updateEmail(email).addOnCompleteListener({ task ->
                                if (task.isSuccessful) {
                                    emitter.onComplete()
                                } else emitter.onError(IOException(task.exception))
                            })
                        },
                        onError = { emitter.onError(handleAuthException(IOException(it))) }
                ))


            } else {
                emitter.onError(handleAuthException())
                emitter.onComplete()
            }
        }
    }

    fun changePassword(newPassword: String, oldPassword: String): Completable {
        return Completable.create { emitter ->
            val user = auth.currentUser
            if (user == null) {
                emitter.onError(handleAuthException(NullPointerException("No selectedUser returned from Firebase")))
                return@create
            }

            if (SystemUtils.isConnected()) {
                emitter.setDisposable(reAuthenticate(user, oldPassword).subscribeBy(
                        onComplete = {
                            user.updatePassword(newPassword).addOnCompleteListener({ task ->
                                if (task.isSuccessful) emitter.onComplete()
                                else task.exception?.let { emitter.onError(handleAuthException(it)) }
                            })
                        },
                        onError = { emitter.onError(handleAuthException(IOException(it))) }
                ))

            } else {
                emitter.onError(handleAuthException())
            }
        }
    }

    fun resetPassword(email: String): Completable {
        return Completable.create { emitter ->
            if (SystemUtils.isConnected()) {
                val action = ActionCodeSettings.newBuilder()
                action.setUrl("https://f7ybe.app.goo.gl/reset")
                        .setAndroidPackageName("pl.ready4s.vanderon", true, null)
                        .setIOSBundleId("com.ready4s.Vanderon.dev")
                        .setHandleCodeInApp(true)

                auth.sendPasswordResetEmail(email, action.build()).addOnCompleteListener({ task ->
                    if (task.isSuccessful) emitter.onComplete()
                    else task.exception?.let { emitter.onError(handleAuthException(it)) }
                })
            } else emitter.onError(handleAuthException())
        }
    }

    fun getIdToken(): Observable<String> {
        return Observable.create { emitter ->
            val user = auth.currentUser
            if (user == null) {
                emitter.onError(handleAuthException(NullPointerException("No selectedUser returned from Firebase")))
                emitter.onComplete()
                return@create
            }
            if (SystemUtils.isConnected()) {
                user.getIdToken(true).addOnCompleteListener({ task ->
                    if (task.isSuccessful) task.result.token?.let { emitter.onNext(it) }
                    else task.exception?.let { emitter.onError(it) }
                    emitter.onComplete()
                })
            } else {
                emitter.onError(handleAuthException())
                emitter.onComplete()
            }
        }
    }

    fun registerAndGetToken(email: String, password: String, fullName: String): Observable<Triple<FirebaseUser, FirebaseUser, String>> {
        return registerCredential(email, password).subscribeOn(RxUtils.io())
                .flatMap { t1 ->
                    updateUser(fullName, null)
                            .flatMap { t2 ->
                                getIdToken()
                                        .flatMap { t3 -> Observable.just(Triple(t1, t2, t3)) }
                            }
                }.onErrorResumeNext(Observable.error(NullPointerException("Something went wrong!")))

    }

    fun logOut() {
        auth.signOut()
    }

}