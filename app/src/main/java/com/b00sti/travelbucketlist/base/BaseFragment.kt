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

    inline fun <reified T : BaseActivity<*, *>> getParent(): T? = activity as? T
    override fun showToast(resMsg: Int) = toast(ResUtils.getString(resMsg))
    override fun showToast(message: String) = toast(message)
    override fun onLoading(loading: Boolean) = getParent<BaseActivity<*, *>>()?.onLoading(loading)

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