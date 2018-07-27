package com.b00sti.travelbucketlist.ui.my_all_lists

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentMyAllListsBinding

/**
 * Created by b00sti on 27.07.2018
 */
class MyAllListsFragment : BaseFragment<FragmentMyAllListsBinding, MyAllListsVM>(), MyAllListsNavigator {

    companion object {
        fun getInstance(): MyAllListsFragment {
            return MyAllListsFragment()
        }
    }

    override fun getViewModels(): MyAllListsVM = ViewModelProviders.of(this).get(MyAllListsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_my_all_lists

}