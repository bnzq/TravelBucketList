package com.b00sti.travelbucketlist.utils

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.b00sti.travelbucketlist.R

/**
 * Created by b00sti on 19.02.2018
 */
class ProgressBarDialog : DialogFragment() {

    companion object {
        fun getInstance() = ProgressBarDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return activity?.layoutInflater?.inflate(R.layout.dialog_progress, container, false)
    }

}