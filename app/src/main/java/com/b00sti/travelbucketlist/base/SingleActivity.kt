package com.b00sti.travelbucketlist.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.databinding.ActivitySingleBinding
import com.b00sti.travelbucketlist.ui.my_bucket_list.MyBucketListFragment
import com.b00sti.travelbucketlist.ui.public_bucket_list.PublicBucketListFragment

/**
 * Created by b00sti on 13.07.2018
 */
class SingleActivity : BaseActivity<ActivitySingleBinding, EmptyViewModel>(), EmptyNavigator {

    companion object {
        var KIND_OF_FRAGMENT_INTENT = "kind_of_fragment"
        var BUNDLE_INTENT = "bundle_to_fragment"

        var EMPTY_FRAGMENT = 0
        var PUBLIC_BUCKET_LIST_FRAGMENT = 1
        var MY_BUCKET_LIST_FRAGMENT = 2

    }


    override fun getViewModels(): EmptyViewModel = ViewModelProviders.of(this).get(EmptyViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_single

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val fragment: Fragment = when (bundle.getInt(KIND_OF_FRAGMENT_INTENT, EMPTY_FRAGMENT)) {
            PUBLIC_BUCKET_LIST_FRAGMENT -> {
                PublicBucketListFragment.getInstance(bundle)
            }
            MY_BUCKET_LIST_FRAGMENT     -> {
                MyBucketListFragment.getInstance(bundle)
            }
            else                        -> {
                EmptyFragment()
            }
        }
        pushFragments(fragment, R.id.vgFragmentPlaceholder, backStack = false, first = true, shouldAnimate = false)
    }

}