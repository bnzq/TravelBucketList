package com.b00sti.travelbucketlist.ui.main.auth.register

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentRegisterBinding
import com.b00sti.travelbucketlist.utils.ScreenRouter

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(), RegisterNavigator {
    companion object {
        fun getInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }

    override fun getViewModels(): RegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_register
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //addViewTransitions(context = getBaseActivity(), enter = android.R.transition.fade, duration = 200L)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setNavigator(this)
        //(getBaseActivity() as AuthActivity).changeBackButton(true)
    }


    override fun registerFacebook() {}//(getBaseActivity() as AuthActivity).onFacebookClick()
    override fun openLoginFragment() = getBaseActivity().onBackPressed()

    override fun openMainActivity() {
        ScreenRouter.goToMainActivity(getBaseActivity())
        getBaseActivity().finish()
    }


}