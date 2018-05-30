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
abstract class BaseViewModel<N : EmptyNavigator> : ViewModel() {
    private lateinit var mNavigator: N
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getNavigator(): N = mNavigator

    fun getBaseNavigator(): BaseNavigator = mNavigator as BaseNavigator

    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }

    fun delayedAction(delay: Long, action: Action) {
        getDisposables().add(Completable.timer(delay, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applyCompletableSchedulers())
                .subscribe(action))
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    open fun onBackBtnClick() {}
    open fun onEditBtnClick() {}

    fun getDisposables(): CompositeDisposable {
        initDisposables()
        return mCompositeDisposable
    }

    fun <T : Any> fetch(observable: Observable<T>, onSuccess: (T) -> Unit = {}, onError: (Throwable) -> Unit = { getBaseNavigator().onError(it) }, onComplete: () -> Unit = {}) {
        initDisposables()
        mCompositeDisposable.add(observable
                .subscribeBy(
                        onNext = { onSuccess.invoke(it) },
                        onError = { onError.invoke(it) },
                        onComplete = { onComplete.invoke() }
                ))
    }

    fun <T : Any> fetchWithPb(observable: Observable<T>, onSuccess: (T) -> Unit = {}, onError: (Throwable) -> Unit = { getBaseNavigator().onError(it) }, onComplete: () -> Unit = {}) {
        initDisposables()
        mCompositeDisposable.add(observable
                .doOnSubscribe({ getBaseNavigator().onStartLoading() })
                .doOnTerminate({ getBaseNavigator().onFinishLoading() })
                .subscribeBy(
                        onNext = { onSuccess.invoke(it) },
                        onError = { onError.invoke(it) },
                        onComplete = { onComplete.invoke() }
                ))
    }

    private fun initDisposables() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
    }

}