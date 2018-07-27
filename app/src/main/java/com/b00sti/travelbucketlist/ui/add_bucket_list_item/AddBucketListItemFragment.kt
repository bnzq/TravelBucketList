package com.b00sti.travelbucketlist.ui.add_bucket_list_item

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentAddBucketListItemBinding

/**
 * Created by b00sti on 27.07.2018
 */
class AddBucketListItemFragment : BaseFragment<FragmentAddBucketListItemBinding, AddBucketListItemVM>(), AddBucketListItemNavigator {

    companion object {
        fun getInstance(): AddBucketListItemFragment {
            return AddBucketListItemFragment()
        }
    }

    override fun getViewModels(): AddBucketListItemVM = ViewModelProviders.of(this).get(AddBucketListItemVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_add_bucket_list_item

}