package com.b00sti.travelbucketlist.ui.countries

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.adapter.CountryItem


/**
 * Created by b00sti on 12.03.2018
 */
class CountriesVM : BaseViewModel<CountriesNavigator>() {

    val countriesList = MutableLiveData<ArrayList<CountryItem>>()

}