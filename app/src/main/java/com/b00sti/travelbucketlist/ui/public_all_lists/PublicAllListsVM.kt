package com.b00sti.travelbucketlist.ui.public_all_lists

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.api.RxFbDatabase
import com.b00sti.travelbucketlist.base.BaseVM
import com.b00sti.travelbucketlist.model.Bucket

/**
 * Created by b00sti on 27.07.2018
 */
class PublicAllListsVM : BaseVM<PublicAllListsNavigator>() {
    val listOfBucketsLists = MutableLiveData<MutableList<Bucket.BucketList>>()

    fun getAllPublicLists() {
        fetchWithPb(RxFbDatabase.getAllPublicBucketLists(), onSuccess = { listOfBucketsLists.postValue(it) })
    }

}