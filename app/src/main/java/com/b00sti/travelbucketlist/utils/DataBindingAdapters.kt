package com.b00sti.travelbucketlist.utils

import android.databinding.BindingAdapter
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by b00sti on 07.02.2018
 */
object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("maxLimitLines")
    fun bindLimitLines(view: EditText, lines: Int) {
        var lastSpecialRequestsCursorPosition = 0
        var specialRequests = ""
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                lastSpecialRequestsCursorPosition = view.selectionStart
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                view.removeTextChangedListener(this)

                if (view.lineCount > lines) {
                    view.setText(specialRequests)
                    view.setSelection(lastSpecialRequestsCursorPosition)
                } else
                    specialRequests = view.text.toString()

                view.addTextChangedListener(this)
            }
        })
    }


/*    @Suppress("unused")
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("avatarUrl")
    fun setAvatarUrl(imageView: ImageView, url: String?) {
        if (url == null || url.trim().isEmpty() || url == "null") imageView.setImageResource(R.drawable.avatar_placeholder)
        else Glide.with(imageView.context).applyDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.avatar_placeholder)
                        .error(R.drawable.avatar_placeholder)
        ).load(url).into(imageView)
    }*/

}