package com.b00sti.travelbucketlist.ui.my_all_lists

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentMyAllListsBinding
import com.b00sti.travelbucketlist.model.Bucket
import com.b00sti.travelbucketlist.ui.my_bucket_list.MyBucketListFragment
import com.b00sti.travelbucketlist.ui.public_all_lists.BucketsListsAdapter
import com.b00sti.travelbucketlist.ui.public_all_lists.BucketsListsNavigator
import com.b00sti.travelbucketlist.utils.ScreenRouter
import kotlinx.android.synthetic.main.fragment_public_all_lists.*

/**
 * Created by b00sti on 27.07.2018
 */
class MyAllListsFragment : BaseFragment<FragmentMyAllListsBinding, MyAllListsVM>(), MyAllListsNavigator {

    companion object {
        fun getInstance(): MyAllListsFragment {
            return MyAllListsFragment()
        }
    }

    private val adapter = BucketsListsAdapter(object : BucketsListsNavigator {
        override fun onItemClicked(item: Bucket.List) {
            ScreenRouter.startWithParams(getBase(), SingleActivity::class, MyBucketListFragment.prepareBundle(item))
        }
    })

    override fun getViewModels(): MyAllListsVM = ViewModelProviders.of(this).get(MyAllListsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_my_all_lists
    override fun initUI() {
        viewModel.setNavigator(this)
        viewModel.listOfBucketsLists.observe(this, Observer { adapter.submitList(it) })
        rvBuckets.layoutManager = LinearLayoutManager(getParent())
        rvBuckets.adapter = adapter
    }

    override fun fetchInitialData() = viewModel.getAllMyLists()


}