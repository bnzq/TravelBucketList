package com.b00sti.travelbucketlist.utils

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.AppCompatImageView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.b00sti.travelbucketlist.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions


/**
 * Created by b00sti on 07.02.2018
 */
object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: AppCompatImageView, url: String) {
        val options = RequestOptions()
        options.fitCenter()
        //val urlFinal = "https://raw.githubusercontent.com/hjnilsson/country-flags/master/png250px/" + url.toLowerCase() + ".png"
        Glide.with(imageView.context)
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                //.placeholder(R.drawable.ic_email_black_24dp)
                //.fitCenter()
                .into(imageView)

    }


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

    @JvmStatic
    @BindingAdapter("validation")
    fun bindError(view: TextInputEditText, valid: Boolean) {
        val drawables = view.compoundDrawablesRelative
        val validation = when {
            view.editableText.toString().isEmpty() -> null
            valid                                  -> ResUtils.getDrawable(R.drawable.ic_done_black_24dp)
            else                                   -> ResUtils.getDrawable(R.drawable.ic_clear_black_24dp)
        }
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables[0], null, validation, null)

    }

    @JvmStatic
    @BindingAdapter("onFocusChange")
    fun bindFocusChange(view: TextInputEditText, focusChangeListener: View.OnFocusChangeListener) {
        view.onFocusChangeListener = focusChangeListener
    }

    @JvmStatic
    @BindingAdapter("formatBase", "formatValue")
    fun setFormat(view: TextView, format: String, value: String) {
        view.visibility = if (value.trim().isEmpty()) View.GONE else View.VISIBLE
        view.text = ViewUtils.fromHtml(String.format(format, value.replace("\n", "<br>")))

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