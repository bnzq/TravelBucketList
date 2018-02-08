package com.b00sti.travelbucketlist.base

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.transition.TransitionInflater
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.utils.ViewUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

/**
 * Created by b00sti on 08.02.2018
 */
abstract class BaseActivity<T : ViewDataBinding, out V : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {

    val viewModel: V by lazy { getViewModels() }
    private lateinit var viewDataBinding: T
    private var progressDialog: DialogFragment? = null
    private var dialog: Dialog? = null
    lateinit var rxPermission: RxPermissions
    protected abstract fun getViewModels(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        performDataBinding()
        rxPermission = RxPermissions(this)
    }

    override fun onStop() {
        super.onStop()
        dialog?.dismiss()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    open fun onNavigateBack(call: () -> Any): Any {
        return if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
            false
        } else {
            call()
        }
    }

    override fun onBackPressed() {
        onNavigateBack { super.onBackPressed() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            onNavigateBack { super.onKeyDown(keyCode, event) } as Boolean
        } else
            super.onKeyDown(keyCode, event)
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


    override fun showError(resMsg: Int, resTitle: Int) {
        // showError(getString(resMsg), getString(resTitle))
    }

    override fun showError(msg: String?, title: String?) {
        onLoading(false)
        //  dialog = ScreenRouter.showSimpleErrorDialog(this, titleText = title
        //         ?: "Error", message = msg ?: "Error, try again.")
    }

    override fun showError(msg: String?, title: String?, listener: (View) -> Unit) {
        onLoading(false)
        // dialog = ScreenRouter.showSimpleErrorDialog(this, titleText = title
        //        ?: "Error", message = msg ?: "Error, try again.", listener = listener)
    }

    override fun onLoading(show: Boolean) {
        when {
        //show -> if (progressDialog == null) progressDialog = DialogFactory.showProgressDialog(this)
            else -> {
                //         DialogFactory.hideProgressDialog(this, progressDialog)
                //        progressDialog = null
            }
        }
    }


    fun requestPermission(vararg perms: String?): Observable<Boolean> {
        return rxPermission.request(*perms)
    }

}