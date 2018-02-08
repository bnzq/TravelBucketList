package com.b00sti.travelbucketlist.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by b00sti on 13.12.2017
 */
object RxUtils {
    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(io())
                    .observeOn(ui())
        }
    }

    fun applyCompletableSchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(io())
                    .observeOn(ui())
        }
    }

    fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(io())
                    .observeOn(ui())
/*                    .doOnNext({ it -> Timber.d("Rx next: " + it) })
                    .doOnComplete({ Timber.d("Rx complete: ") })
                    .doOnSubscribe({ it -> Timber.d("Rx subscribe: " + it) })
                    .doOnDispose({ Timber.d("Rx unsubscribe: ") })
                    .doOnError({ it -> Timber.d("Rx on error: " + it.toString()) })*/
        }
    }

    fun <T> applyFlowableSchedulers(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(io())
                    .observeOn(ui())
        }
    }

    fun ui(): Scheduler = AndroidSchedulers.mainThread()
    fun io(): Scheduler = Schedulers.io()
    fun computation(): Scheduler = Schedulers.computation()
}