package com.b00sti.travelbucketlist.ui.auth.register

import com.b00sti.travelbucketlist.base.BaseNav

interface RegisterNavigator : BaseNav {

    fun registerFacebook()
    fun openLoginFragment()
    fun openMainActivity()

}