package com.b00sti.travelbucketlist.utils

/**
 * Created by b00sti on 07.02.2018
 */
object AppConstants {

    const val TIMEOUT_DOUBLE_TAP = 2000L

    const val EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,32})$"
    const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d$@$!%*?&]{8,32}"
    const val USERNAME_REGEX = "^[\\p{L} .'-]+$"
    const val ABOUT_ME_REGEX = "^[\\p{L}\\d .'-,!?]+\$"

    const val CODE_PICK_PHOTO = 1046

    const val ANIMATION_MEDIUM_TIME = 500L
    const val ANIMATION_SHORT_TIME = 1000L
    const val ANIMATION_LONG_TIME = 200L

}