package com.b00sti.travelbucketlist.ui.add_bucket_list

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentAddBucketListBinding

/**
 * Created by b00sti on 27.07.2018
 */
class AddBucketListFragment : BaseFragment<FragmentAddBucketListBinding, AddBucketListVM>(), AddBucketListNavigator {

    companion object {
        fun getInstance(): AddBucketListFragment {
            return AddBucketListFragment()
        }
    }

    override fun getViewModels(): AddBucketListVM = ViewModelProviders.of(this).get(AddBucketListVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_add_bucket_list

}