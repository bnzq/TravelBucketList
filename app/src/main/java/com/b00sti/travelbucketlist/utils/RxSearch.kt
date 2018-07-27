package com.b00sti.travelbucketlist.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

/**
 * Created by b00sti on 07.02.2018
 */
class RxSearch {

    private val subject = BehaviorSubject.create<String>()
    private var disposable: Disposable? = null

    fun fromEditText(editText: EditText, call: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                editText.removeTextChangedListener(this)
                if (editText.text.toString().startsWith(" ")) editText.setText("")
                subject.onNext(editText.text.toString().trim())
                editText.addTextChangedListener(this)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        disposable = subject
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(mainThread())
                .subscribeBy(onNext = { s -> call(s) })
    }

    fun removeSub(editText: EditText) {
        disposable?.dispose()
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

}