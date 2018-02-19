package com.b00sti.travelbucketlist.ui.auth

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseActivity
import com.b00sti.travelbucketlist.databinding.ActivityAuthBinding
import com.b00sti.travelbucketlist.ui.auth.login.LoginFragment

/**
 * Created by b00sti on 13.02.2018
 */
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>(), AuthNavigator {

    override fun onBackClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openMainActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewModels(): AuthViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushFragments(LoginFragment.getInstance(), R.id.flAuthContainer, first = true, shouldAnimate = false)
        viewModel.setNavigator(this)
/*        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val uri = task.result.link
                Timber.w(uri.toString())
                if (uri.getQueryParameter("mode") == "resetPassword") {
                    //pushFragments(ResetFragment.getInstance(uri.getQueryParameter("oobCode")), R.id.flAuthContainer, true)
                }
            }
        }*/
    }

}