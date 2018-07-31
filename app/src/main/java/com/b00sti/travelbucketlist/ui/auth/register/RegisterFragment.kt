package com.b00sti.travelbucketlist.ui.auth.register

import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentRegisterBinding
import com.b00sti.travelbucketlist.ui.auth.AuthActivity
import com.b00sti.travelbucketlist.ui.auth.login.LoginFragment
import com.b00sti.travelbucketlist.utils.ScreenRouter
import com.b00sti.travelbucketlist.utils.finish
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(), RegisterNavigator {

    companion object {
        fun getInstance(): RegisterFragment = RegisterFragment()
    }

    override fun getViewModels(): RegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_register
    override fun initUI() = viewModel.setNavigator(this)
    override fun fetchInitialData() {}
    override fun registerFacebook() = getParent<AuthActivity>()?.onFacebookClick() ?: Unit
    override fun openLoginFragment() {
        var fragment = getBase()?.supportFragmentManager?.findFragmentByTag(LoginFragment::class.java.name)
        var isFragmentAdded = true
        if (fragment == null) {
            isFragmentAdded = false
            fragment = LoginFragment.getInstance()
        }
        getBase()?.pushFragments(
                fragment,
                R.id.flAuthContainer,
                backStack = !isFragmentAdded,
                shouldAnimate = false,
                transition = true,
                view = getSharedViews())

    }


    override fun openMainActivity() {
        ScreenRouter.goToMainActivity(getBase())
        finish()
    }

    private fun getSharedViews(): ArrayList<View?> {
        val views = ArrayList<View?>()
        views.add(vBackgroundHeaderRegister)
        views.add(ivAppLogoRegister)
        views.add(vgCredentials)
        views.add(tilEmailRegister)
        views.add(tilPasswordRegister)
        return views
    }

}