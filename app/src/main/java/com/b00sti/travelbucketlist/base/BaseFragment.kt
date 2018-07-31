package com.b00sti.travelbucketlist.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.b00sti.travelbucketlist.utils.ResUtils
import com.b00sti.travelbucketlist.utils.toast

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseFragment<T : ViewDataBinding, out V : BaseVM<*>> : Fragment(), BaseNav {

    private lateinit var viewDataBinding: T
    val viewModel: V by lazy { getViewModels() }
    private lateinit var mRootView: View

    protected abstract fun getViewModels(): V
    protected abstract fun getBindingVariable(): Int
    protected abstract fun initUI(): Unit?
    protected abstract fun fetchInitialData(): Unit?
    @LayoutRes protected abstract fun getLayoutId(): Int

    open fun refresh(): Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prepareDataBindingLayout(inflater, container)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDataBindingVariables()
        initUI()
        fetchInitialData()
    }

    private fun prepareDataBindingLayout(inflater: LayoutInflater, container: ViewGroup?) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding.root
    }

    private fun prepareDataBindingVariables() {
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    override fun onError(throwable: Throwable) {

    }

    inline fun <reified T : BaseActivity<*, *>> getParent(): T? = activity as? T
    fun getBase(): BaseActivity<*, *>? = activity as BaseActivity<*, *>
    inline fun <reified T> getFromBundle(key: String = SingleActivity.BUNDLE_INTENT): T? = arguments!![key] as? T
    override fun showToast(resMsg: Int) = toast(ResUtils.getString(resMsg))
    override fun showToast(message: String) = toast(message)
    override fun onStartLoading() = getParent<BaseActivity<*, *>>()?.onStartLoading()
    override fun onFinishLoading() = getParent<BaseActivity<*, *>>()?.onFinishLoading()
    override fun showErrorDialog(resMsg: Int, resTitle: Int) = getBase()?.showErrorDialog(resMsg, resTitle) ?: Unit
    override fun showErrorDialog(msg: String?, title: String?) = getBase()?.showErrorDialog(msg, title) ?: Unit
    override fun showErrorDialog(msg: String?, title: String?, listener: (View) -> Unit) = getBase()?.showErrorDialog(msg, title, listener) ?: Unit

    @JvmOverloads
    fun addViewTransitions(context: Context? = getContext(), enter: Int, exit: Int = enter, duration: Long) {
        allowEnterTransitionOverlap = false
        allowReturnTransitionOverlap = false
        val enterTrans = TransitionInflater.from(context).inflateTransition(enter)
        enterTrans.duration = duration
        val exitTrans = TransitionInflater.from(context).inflateTransition(exit)
        exitTrans.duration = duration / 2
        enterTransition = enterTrans
        exitTransition = exitTrans
    }

}