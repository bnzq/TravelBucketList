package com.b00sti.travelbucketlist.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.transition.TransitionInflater
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.utils.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseActivity<T : ViewDataBinding, out V : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {

    val viewModel: V by lazy { getViewModels() }
    private lateinit var viewDataBinding: T
    lateinit var rxPermission: RxPermissions
    private var pb: DialogFragment? = null
    protected abstract fun getViewModels(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    val DOUBLE_TAP_TIMEOUT = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        performDataBinding()
        rxPermission = RxPermissions(this)
    }

    override fun onStop() {
        super.onStop()
        pb?.dismiss()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    override fun showToast(resMsg: Int) = toast(ResUtils.getString(resMsg))
    override fun showToast(message: String) = toast(message)
    override fun onLoading(loading: Boolean) {
        return when {
            loading ->
                if (pb == null) {
                    pb = showProgressDialog()
                } else {
                    return
                }
            else -> hideProgressDialog(pb)
        }
    }

    override fun showErrorDialog(resMsg: Int, resTitle: Int) {
        ScreenRouter.showSimpleErrorDialog(this, ResUtils.getString(resMsg), ResUtils.getString(resTitle))
    }

    override fun showErrorDialog(msg: String?, title: String?) {
        ScreenRouter.showSimpleErrorDialog(this, msg, title)
    }

    override fun showErrorDialog(msg: String?, title: String?, listener: (View) -> Unit) {
        ScreenRouter.showSimpleErrorDialog(this, title, title, listener = listener)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentFocus?.let {
            it.clearFocus()
            ViewUtils.hideSoftInput(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    @JvmOverloads
    fun pushFragments(fragment: Fragment, @IdRes content: Int,
                      shouldAnimate: Boolean = true,
                      backStack: Boolean = true,
                      transition: Boolean = false,
                      first: Boolean = false,
                      view: ArrayList<View?> = ArrayList()) {
        val manager = supportFragmentManager

        val ft = manager.beginTransaction()
        var fragmentPopped = false
        try {
            fragmentPopped = manager.popBackStackImmediate(fragment::class.java.name, 0)
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }

        if (!fragmentPopped) {
            if (shouldAnimate) ft.setCustomAnimations(if (!first) R.anim.slide_in_right else 0,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right)

            if (backStack) ft.addToBackStack(fragment::class.java.name)

            if (transition) {
                fragment.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
                fragment.sharedElementReturnTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
                for (v in view) {
                    v?.let { it.transitionName?.let { ft.addSharedElement(v, it) } }
                }
            }
        }
        ft.replace(content, fragment, fragment::class.java.name)

        try {

            ft.commit()
            manager.executePendingTransactions()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun requestPermission(vararg perms: String?): Observable<Boolean> {
        return rxPermission.request(*perms)
    }

}