package com.b00sti.travelbucketlist.ui.auth.login

import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentLoginBinding
import com.b00sti.travelbucketlist.ui.auth.AuthActivity
import com.b00sti.travelbucketlist.ui.auth.register.RegisterFragment
import com.b00sti.travelbucketlist.utils.ScreenRouter
import com.b00sti.travelbucketlist.utils.finish
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * Created by b00sti on 15.02.2018
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), LoginNavigator {

    companion object {
        fun getInstance(): LoginFragment = LoginFragment()
    }

    override fun getViewModels(): LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_login
    override fun initUI() = viewModel.setNavigator(this)
    override fun fetchInitialData() {}

    override fun loginFacebook() = getParent<AuthActivity>()?.onFacebookClick() ?: Unit

    override fun openMainActivity() {
        ScreenRouter.goToMainActivity(getBase())
        finish()
    }

    override fun openRegisterFragment() {
        var fragment = getBase()?.supportFragmentManager?.findFragmentByTag(RegisterFragment::class.java.name)
        var isFragmentAdded = true
        if (fragment == null) {
            isFragmentAdded = false
            fragment = RegisterFragment.getInstance()
        }
        getBase()?.pushFragments(
                fragment,
                R.id.flAuthContainer,
                backStack = !isFragmentAdded,
                shouldAnimate = false,
                transition = true,
                view = getSharedViews())

    }

    override fun openForgotFragment() {

    }

    private fun getSharedViews(): ArrayList<View?> {
        val views = ArrayList<View?>()
        views.add(vBackgroundHeaderLogin)
        views.add(ivAppLogoLogin)
        views.add(vgCredentialsLogin)
        views.add(tilEmailLogin)
        views.add(tilPasswordLogin)
        return views
    }

    private fun makeForgotSharedViews() {
    }
}