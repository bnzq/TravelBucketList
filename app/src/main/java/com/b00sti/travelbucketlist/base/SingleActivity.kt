package com.b00sti.travelbucketlist.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.databinding.ActivitySingleBinding
import com.b00sti.travelbucketlist.ui.bucket_list_to_copy.BucketCopyFragment

/**
 * Created by b00sti on 13.07.2018
 */
class SingleActivity : BaseActivity<ActivitySingleBinding, EmptyViewModel>(), EmptyNavigator {

    override fun getViewModels(): EmptyViewModel = ViewModelProviders.of(this).get(EmptyViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_single

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushFragments(BucketCopyFragment.getInstance(), R.id.vgFragmentPlaceholder, backStack = false, first = true, shouldAnimate = false)
    }

}