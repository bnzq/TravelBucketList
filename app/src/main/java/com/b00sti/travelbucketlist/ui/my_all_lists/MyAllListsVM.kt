package com.b00sti.travelbucketlist.ui.my_all_lists

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.api.RxFbDatabase
import com.b00sti.travelbucketlist.base.BaseVM
import com.b00sti.travelbucketlist.model.Bucket

/**
 * Created by b00sti on 27.07.2018
 */
class MyAllListsVM : BaseVM<MyAllListsNavigator>() {

    val listOfBucketsLists = MutableLiveData<MutableList<Bucket.List>>()

    fun getAllMyLists() {
        fetch(RxFbDatabase.getAllMyBucketLists(), onSuccess = { listOfBucketsLists.postValue(it) })
    }

}