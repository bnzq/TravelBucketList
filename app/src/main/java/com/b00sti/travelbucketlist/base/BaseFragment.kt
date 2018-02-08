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

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseFragment<T : ViewDataBinding, out V : BaseViewModel<*>> : Fragment(), BaseNavigator {
    private lateinit var viewDataBinding: T
    val viewModel: V by lazy { getViewModels() }
    private lateinit var mRootView: View
    private lateinit var mActivity: BaseActivity<*, *>
    var allowBackPress = true

    protected abstract fun getViewModels(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity<*, *>
    }

    open fun refresh(): Boolean {
        return false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }


    protected fun getBaseActivity(): BaseActivity<*, *> = mActivity

    override fun showError(resMsg: Int, resTitle: Int) = getBaseActivity().showError(resMsg, resTitle)
    override fun showError(msg: String?, title: String?) = getBaseActivity().showError(msg, title)
    override fun onLoading(show: Boolean) = getBaseActivity().onLoading(show)
    override fun showError(msg: String?, title: String?, listener: (View) -> Unit) = getBaseActivity().showError(msg, title, listener)

    @JvmOverloads
    fun addViewTransitions(context: Context = getBaseActivity(), enter: Int, exit: Int = enter, duration: Long) {
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