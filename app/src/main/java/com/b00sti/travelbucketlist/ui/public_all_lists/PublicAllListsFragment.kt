package com.b00sti.travelbucketlist.ui.public_all_lists

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentPublicAllListsBinding
import com.b00sti.travelbucketlist.model.Bucket
import com.b00sti.travelbucketlist.ui.public_bucket_list.PublicBucketListFragment
import com.b00sti.travelbucketlist.utils.ScreenRouter
import kotlinx.android.synthetic.main.fragment_public_all_lists.*

/**
 * Created by b00sti on 27.07.2018
 */
class PublicAllListsFragment : BaseFragment<FragmentPublicAllListsBinding, PublicAllListsVM>(), PublicAllListsNavigator {


    companion object {
        fun getInstance(): PublicAllListsFragment {
            return PublicAllListsFragment()
        }
    }

    private val adapter = BucketsListsAdapter(object : BucketsListsNavigator {
        override fun onItemClicked(item: Bucket.List) {
            ScreenRouter.startWithParams(getBase(), SingleActivity::class, PublicBucketListFragment.prepareBundle(item))
        }
    })

    override fun getViewModels(): PublicAllListsVM = ViewModelProviders.of(this).get(PublicAllListsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_public_all_lists

    override fun initUI() {
        viewModel.setNavigator(this)
        viewModel.listOfBucketsLists.observe(this, Observer { adapter.submitList(it) })
        rvBuckets.layoutManager = LinearLayoutManager(getParent())
        rvBuckets.adapter = adapter
    }

    override fun fetchInitialData() = viewModel.getAllPublicLists()

}
