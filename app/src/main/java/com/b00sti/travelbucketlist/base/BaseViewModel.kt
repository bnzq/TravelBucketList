package com.b00sti.travelbucketlist.base

import android.arch.lifecycle.ViewModel
import com.b00sti.travelbucketlist.utils.RxUtils
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import java.util.concurrent.TimeUnit

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseViewModel<N : Any> : ViewModel() {
    private lateinit var mNavigator: N
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()


    fun getNavigator(): N = mNavigator

    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }

    fun delayedAction(delay: Long, action: Action) {
        getCompositeDisposable().add(Completable.timer(delay, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applyCompletableSchedulers()).subscribe(action))
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }

    open fun onBackBtnClick() {}
    open fun onEditBtnClick() {}

    fun getCompositeDisposable(): CompositeDisposable {
        if (mCompositeDisposable.isDisposed) mCompositeDisposable = CompositeDisposable()
        return mCompositeDisposable
    }

}