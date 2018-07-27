package com.b00sti.travelbucketlist.ui.public_all_lists

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentPublicAllListsBinding
import com.b00sti.travelbucketlist.model.Bucket
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

    override fun getViewModels(): PublicAllListsVM = ViewModelProviders.of(this).get(PublicAllListsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_public_all_lists

    private val adapter = BucketsListsAdapter(object : BucketsListsNavigator {
        override fun onItemClicked(item: Bucket.BucketList) {
            ScreenRouter.startWithoutParams(getBase(), SingleActivity::class)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
        viewModel.getAllPublicLists()
    }

    private fun initUI() {
        viewModel.listOfBucketsLists.observe(this, Observer { adapter.submitList(it) })
        rvBuckets.layoutManager = LinearLayoutManager(getParent())
        rvBuckets.adapter = adapter
    }

}