package com.b00sti.travelbucketlist.ui.countries

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.adapter.CONTINENTS
import com.b00sti.travelbucketlist.utils.adapter.CountryItem
import io.reactivex.Observable


/**
 * Created by b00sti on 12.03.2018
 */
class CountriesVM : BaseViewModel<CountriesNavigator>() {

    val countriesList = MutableLiveData<List<CountryItem>>()

    fun removeItem(item: CountryItem) {
        val list = countriesList.value?.toMutableList()
        list?.contains(item)
        list?.remove(item)
        countriesList.postValue(list)
    }

    fun refresh() {
        getNavigator().onRefreshCompleted()
        //fetchWithPb(getCountries(), { countriesList.postValue(it) })
    }

    fun getCountries(): Observable<List<CountryItem>> {
        return Observable.create { emitter ->
            val items = listOf(CountryItem("Poland" + System.currentTimeMillis(), CONTINENTS.EUROPE, true, ""),
                    CountryItem("France" + System.currentTimeMillis(), CONTINENTS.EUROPE, false, ""),
                    CountryItem("Spain" + System.currentTimeMillis(), CONTINENTS.EUROPE, true, ""))
            val list = countriesList.value?.toMutableList()
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