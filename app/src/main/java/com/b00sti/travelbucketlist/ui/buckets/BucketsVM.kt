package com.b00sti.travelbucketlist.ui.buckets

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.adapter.BucketItem
import io.reactivex.Observable

/**
 * Created by b00sti on 20.04.2018
 */
class BucketsVM : BaseViewModel<BucketsNavigator>() {

    val bucketsList = MutableLiveData<List<BucketItem>>()

    fun refresh() {
        fetchWithPb(getBuckets(), { bucketsList.postValue(it) })
    }

    fun getBuckets(): Observable<List<BucketItem>> {
        return Observable.create { emitter ->
            val items = listOf(
                    BucketItem("Europe" + System.currentTimeMillis(), 10, 20, ""),
                    BucketItem("Asia" + System.currentTimeMillis(), 10, 20, ""),
                    BucketItem("Africa" + System.currentTimeMillis(), 10, 20, "")
            )
            val list = bucketsList.value?.toMutableList()
            list?.addAll(items)
            if (list != null) {
                emitter.onNext(list)
                //countriesList.postValue(list)
            } else {
                emitter.onNext(items)
                //countriesList.postValue(items)
            }
            emitter.onComplete()
            //emitter.onError(Throwable())
        }
    }

}