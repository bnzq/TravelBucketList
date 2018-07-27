package com.b00sti.travelbucketlist.ui.auth

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseActivity
import com.b00sti.travelbucketlist.databinding.ActivityAuthBinding
import com.b00sti.travelbucketlist.ui.auth.login.LoginFragment
import com.b00sti.travelbucketlist.utils.RxFacebook
import com.b00sti.travelbucketlist.utils.RxUtils
import com.b00sti.travelbucketlist.utils.ScreenRouter
import com.b00sti.travelbucketlist.utils.SystemUtils
import io.reactivex.rxkotlin.subscribeBy
import java.util.*


/**
 * Created by b00sti on 13.02.2018
 */
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>(), AuthNavigator {

    override fun onBackClick() {

    }

    override fun openMainActivity() {
        ScreenRouter.goToMainActivity(this)
    }

    override fun getViewModels(): AuthViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_auth

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            RxFacebook.postLoginActivityResult(requestCode, resultCode, data)
        }
    }

    fun onFacebookClick() {
        if (SystemUtils.isConnected()) {
            RxFacebook.create().loginWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_friends"))
                    .compose(RxUtils.applyObservableSchedulers()).
                    subscribeBy(
                            onNext = {
                                viewModel.handleFacebookResult(it)
                            },
                            onError = {
                                it.printStackTrace()
                                showErrorDialog("Failed to log in with facebook,\n try again or choose another method!")
                            }
                    )
        } else showErrorDialog(R.string.no_connection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushFragments(LoginFragment.getInstance(), R.id.flAuthContainer, backStack = false, first = true, shouldAnimate = false)
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