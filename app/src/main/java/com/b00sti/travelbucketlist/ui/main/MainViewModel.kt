package com.b00sti.travelbucketlist.ui.main

import com.b00sti.travelbucketlist.base.BaseViewModel

/**
 * Created by b00sti on 08.02.2018
 */
class MainViewModel : BaseViewModel<MainNavigator>() {

    fun onBackArrowClick() {
        navigate().onBackArrowClick()
    }

    fun onSettingsClicked() {
        navigate().onBackArrowClick()
    }
}