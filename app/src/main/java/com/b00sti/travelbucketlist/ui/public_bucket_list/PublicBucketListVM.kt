package com.b00sti.travelbucketlist.ui.public_bucket_list

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.api.RxFbDatabase
import com.b00sti.travelbucketlist.base.BaseVM
import com.b00sti.travelbucketlist.model.Bucket

/**
 * Created by b00sti on 27.07.2018
 */
class PublicBucketListVM : BaseVM<PublicBucketListNavigator>() {

    val listOfBuckets = MutableLiveData<MutableList<Bucket.ToDo>>()
    lateinit var bucketList: Bucket.List

    fun onCopyClicked() {
        fetch(RxFbDatabase.copyBucketList(bucketList), onComplete = {
            getNavigator().onCopyCompleted()
        })
    }

    fun getBucketList() {
        fetch(RxFbDatabase.getBucketList(bucketList), onSuccess = {
            val updateList: MutableList<Bucket.ToDo> = mutableListOf()
            updateList.addAll(listOfBuckets.value ?: mutableListOf())
            updateList.addAll(it)
            listOfBuckets.postValue(updateList)
        })
    }

}