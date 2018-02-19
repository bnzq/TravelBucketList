package com.b00sti.travelbucketlist.ui.auth.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentLoginBinding
import com.b00sti.travelbucketlist.ui.auth.register.RegisterFragment
import com.b00sti.travelbucketlist.utils.ScreenRouter
import com.b00sti.travelbucketlist.utils.finish
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Created by b00sti on 15.02.2018
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), LoginNavigator {

    private val views = ArrayList<View?>()

    companion object {
        fun getInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun getViewModels(): LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //addViewTransitions(enter = android.R.transition.fade, duration = 400L)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setNavigator(this)
    }

    override fun loginFacebook() {}//(getBaseActivity() as AuthActivity).onFacebookClick()

    override fun openMainActivity() {
        ScreenRouter.goToMainActivity(getBase())
        finish()
    }

    override fun openRegisterFragment() {
        //makeRegisterSharedViews()
        if (views.contains(null)) return
        val register = RegisterFragment.getInstance()
/*        register.sharedElementEnterTransition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move)
        register.sharedElementReturnTransition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move)*/
        getBase()?.pushFragments(register, R.id.flAuthContainer, shouldAnimate = false)
    }

    override fun openForgotFragment() {
        //makeForgotSharedViews()
        if (views.contains(null)) return
        //val forgot = ForgotFragment.getInstance()
/*        forgot.addViewTransitions(context = getBaseActivity(), enter = android.R.transition.fade, duration = 300L)
        forgot.sharedElementEnterTransition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move)
        forgot.sharedElementReturnTransition = TransitionInflater.from(getBaseActivity()).inflateTransition(android.R.transition.move)
        getBaseActivity().pushFragments(forgot, R.id.flAuthContainer, transition = true, shouldAnimate = false, view = views)*/
    }

    private fun makeRegisterSharedViews() {
        views.clear()
        views.add(tietEmailLogin)
        views.add(tietPasswordLogin)
        views.add(buttonTvLogin)
        views.add(tvSocialInfoLogin)
        views.add(buttonFacebookLogin)
        views.add(buttonRegisterLogin)
        views.add(tvRegisterInfoLogin)
        views.add(ivAppLogoLogin)
        views.add(vgCredentials)
        views.add(vBackgroundHeaderLogin)
    }

    private fun makeForgotSharedViews() {
        views.clear()
        views.add(tietEmailLogin)
        views.add(buttonTvLogin)
    }
}