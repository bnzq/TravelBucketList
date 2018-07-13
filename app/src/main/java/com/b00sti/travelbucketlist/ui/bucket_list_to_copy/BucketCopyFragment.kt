package com.b00sti.travelbucketlist.ui.bucket_list_to_copy

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentBucketCopyBinding

/**
 * Created by b00sti on 13.07.2018
 */
class BucketCopyFragment : BaseFragment<FragmentBucketCopyBinding, BucketCopyVM>(), BucketCopyNavigator {

    companion object {
        fun getInstance(): BucketCopyFragment {
            return BucketCopyFragment()
        }
    }

    override fun getViewModels(): BucketCopyVM = ViewModelProviders.of(this).get(BucketCopyVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_bucket_copy

}
