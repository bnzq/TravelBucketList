package com.b00sti.travelbucketlist.ui.my_bucket_item_details

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentMyBucketItemDetailsBinding

/**
 * Created by b00sti on 27.07.2018
 */
class MyBucketItemDetailsFragment : BaseFragment<FragmentMyBucketItemDetailsBinding, MyBucketItemDetailsVM>(), MyBucketItemDetailsNavigator {

    companion object {
        fun getInstance(): MyBucketItemDetailsFragment {
            return MyBucketItemDetailsFragment()
        }
    }

    override fun getViewModels(): MyBucketItemDetailsVM = ViewModelProviders.of(this).get(MyBucketItemDetailsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_my_bucket_item_details

}