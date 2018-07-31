package com.b00sti.travelbucketlist.ui.public_bucket_item_details

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentPublicBucketItemDetailsBinding

/**
 * Created by b00sti on 27.07.2018
 */
class PublicBucketItemDetailsFragment : BaseFragment<FragmentPublicBucketItemDetailsBinding, PublicBucketItemDetailsVM>(), PublicBucketItemDetailsNavigator {

    companion object {
        fun getInstance(): PublicBucketItemDetailsFragment {
            return PublicBucketItemDetailsFragment()
        }
    }

    override fun getViewModels(): PublicBucketItemDetailsVM = ViewModelProviders.of(this).get(PublicBucketItemDetailsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_public_bucket_item_details

    override fun initUI() {}
    override fun fetchInitialData() {}

}