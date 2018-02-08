package com.b00sti.travelbucketlist.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseViewHolder<out T : ViewDataBinding, V : BaseViewModel<*>>(private val viewDataBinding: T) : RecyclerView.ViewHolder(viewDataBinding.root) {
    lateinit var viewModel: V
    protected abstract fun getViewModel(position: Int): V
    protected abstract fun getBindingVariable(): Int

    open fun onBind(position: Int) {
        viewModel = getViewModel(position)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

}