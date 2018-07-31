package com.b00sti.travelbucketlist.ui.my_bucket_list

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.api.RxFbDatabase
import com.b00sti.travelbucketlist.base.BaseVM
import com.b00sti.travelbucketlist.model.Bucket

/**
 * Created by b00sti on 27.07.2018
 */
class MyBucketListVM : BaseVM<MyBucketListNavigator>() {

    val listOfBuckets = MutableLiveData<MutableList<Bucket.ToDo>>()
    lateinit var bucketList: Bucket.List

    fun getBucketList() {
        fetch(RxFbDatabase.getMyBucketList(bucketList), onSuccess = {
            val updateList: MutableList<Bucket.ToDo> = mutableListOf()
            updateList.addAll(listOfBuckets.value ?: mutableListOf())
            updateList.addAll(it)
            listOfBuckets.postValue(updateList)
        })
    }

}