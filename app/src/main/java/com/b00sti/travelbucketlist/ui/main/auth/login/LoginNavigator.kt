package com.b00sti.travelbucketlist.ui.main.auth.login

import com.b00sti.travelbucketlist.base.BaseNavigator

/**
 * Created by b00sti on 15.02.2018
 */
interface LoginNavigator : BaseNavigator {
    fun loginFacebook()
    fun openRegisterFragment()
    fun openMainActivity()
    fun openForgotFragment()
}