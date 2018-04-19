package com.b00sti.travelbucketlist.utils

import com.google.firebase.auth.*
import java.io.IOException
import java.net.ConnectException

/**
 * Created by b00sti on 19.04.2018
 */
object ExceptionsCenter {

    fun handleException(e: Throwable): Throwable {
        e.printStackTrace()
        return when (e) {
            is ConnectException -> IOException("No Internet Connection")
            is FirebaseAuthInvalidUserException -> IOException("User does not exist!")
            is FirebaseAuthEmailException -> IOException("Invalid Email!")
            is FirebaseAuthInvalidCredentialsException -> IOException("Invalid email or password!")
            is FirebaseAuthUserCollisionException -> when (e.errorCode) {
                "ERROR_USER_NOT_FOUND" -> IOException("User not found")
                "ERROR_USER_DISABLED" -> IOException("User disabled")
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> IOException("Logged in as other selectedUser")
                else -> IOException(e.message)
            }
            is FirebaseAuthWeakPasswordException -> IOException("Password is too weak.")
            is FirebaseAuthRecentLoginRequiredException -> IOException(e.message)
            is NullPointerException -> IOException("")
            else -> IOException("Error! Try again.")
        }
    }

}