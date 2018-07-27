package com.b00sti.travelbucketlist.ui.my_bucket_list

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentMyBucketListBinding

/**
 * Created by b00sti on 27.07.2018
 */
class MyBucketListFragment : BaseFragment<FragmentMyBucketListBinding, MyBucketListVM>(), MyBucketListNavigator {

    companion object {
        fun getInstance(): MyBucketListFragment {
            return MyBucketListFragment()
        }
    }

    override fun getViewModels(): MyBucketListVM = ViewModelProviders.of(this).get(MyBucketListVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_my_bucket_list

}