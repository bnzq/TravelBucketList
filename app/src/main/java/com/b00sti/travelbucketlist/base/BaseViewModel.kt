package com.b00sti.travelbucketlist.base

import android.arch.lifecycle.ViewModel
import com.b00sti.travelbucketlist.utils.RxUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseViewModel<N : BaseNavigator> : ViewModel() {
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

    fun <T : Any> fetch(d: Observable<T>, onSuccess: (T) -> Unit = {}, onError: (Throwable) -> Unit = { getNavigator().onError(it) }, onComplete: () -> Unit = {}) {
        if (mCompositeDisposable.isDisposed) mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable.add(d.subscribeBy(
                onNext = { onSuccess.invoke(it) },
                onError = { onError.invoke(it) },
                onComplete = { onComplete.invoke() }
        ))
    }

    fun <T : Any> fetchWithPb(observable: Observable<T>, onSuccess: (T) -> Unit = {}, onError: (Throwable) -> Unit = { getNavigator().onError(it) }, onComplete: () -> Unit = {}) {
        if (mCompositeDisposable.isDisposed) mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable.add(
                observable
                        .doOnSubscribe({ getNavigator().onLoading(true) })
                        .doOnTerminate({ getNavigator().onLoading(false) })
                        .subscribeBy(
                                onNext = { onSuccess.invoke(it) },
                                onError = { onError.invoke(it) },
                                onComplete = { onComplete.invoke() }
                        ))
    }

}