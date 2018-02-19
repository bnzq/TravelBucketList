package com.b00sti.travelbucketlist.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.WindowManager
import com.b00sti.travelbucketlist.R

/**
 * Created by b00sti on 19.02.2018
 */
object DialogFactory {
    private const val DEFAULT_DIALOG_MESSAGE = "Please try again"
    private const val DEFAULT_DIALOG_TITLE = "Error"
    private const val DEFAULT_DIALOG_RIGHT_BTN = "OK"
    private const val DEFAULT_DIALOG_LEFT_BTN = "CANCEL"

    fun showSimpleDialog(context: Context?,
                         message: String? = DEFAULT_DIALOG_MESSAGE,
                         titleText: String? = DEFAULT_DIALOG_TITLE,
                         rightBtnText: String? = DEFAULT_DIALOG_RIGHT_BTN,
                         rightBtnListener: (View) -> Unit = ScreenRouter.EMPTY_METHOD,
                         rightBtnDismiss: Boolean = false,
                         leftBtnEnabled: Boolean = false,
                         leftBtnText: String? = DEFAULT_DIALOG_LEFT_BTN,
                         leftBtnListener: (View) -> Unit = ScreenRouter.EMPTY_METHOD,
                         leftBtnDismiss: Boolean = false): Dialog? {
        when {
            context != null -> {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_default)
                val btnRightDialog = dialog.findViewById<AppCompatButton>(R.id.btnRight) as AppCompatButton
                val btnLeftDialog = dialog.findViewById<AppCompatButton>(R.id.btnLeft) as AppCompatButton
                val tvTitle = dialog.findViewById<AppCompatTextView>(R.id.toolbarTitleTv) as AppCompatTextView
                val tvMessage = dialog.findViewById<AppCompatTextView>(R.id.tvMessage) as AppCompatTextView
                tvTitle.text = titleText
                tvMessage.text = message
                btnRightDialog.text = rightBtnText
                if (leftBtnEnabled) {
                    btnLeftDialog.text = leftBtnText
                    btnLeftDialog.visible()
                }
                when (rightBtnListener) {
                    ScreenRouter.EMPTY_METHOD -> btnRightDialog.setOnClickListener({ dialog.dismiss() })
                    else -> btnRightDialog.setOnClickListener({
                        rightBtnListener(it)
                        if (rightBtnDismiss) dialog.dismiss()
                    })
                }
                when (leftBtnListener) {
                    ScreenRouter.EMPTY_METHOD -> btnLeftDialog.setOnClickListener({ dialog.dismiss() })
                    else -> btnLeftDialog.setOnClickListener({
                        leftBtnListener(it)
                        if (leftBtnDismiss) dialog.dismiss()
                    })
                }
                dialog.show()

                val lp = WindowManager.LayoutParams()
                val window = dialog.window
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                lp.copyFrom(window.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                window.attributes = lp
                return dialog
            }
            else -> return null
        }
    }

}
