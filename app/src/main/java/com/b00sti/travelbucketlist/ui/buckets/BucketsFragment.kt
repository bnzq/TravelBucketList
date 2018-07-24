package com.b00sti.travelbucketlist.ui.buckets

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.api.RxFirebaseDatabase
import com.b00sti.travelbucketlist.base.AdapterNavigator
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentBucketsBinding
import com.b00sti.travelbucketlist.utils.adapter.BucketItem
import com.b00sti.travelbucketlist.utils.adapter.BucketsAdapter
import com.b00sti.travelbucketlist.utils.toast
import kotlinx.android.synthetic.main.fragment_buckets.*

/**
 * Created by b00sti on 20.04.2018
 */
class BucketsFragment : BaseFragment<FragmentBucketsBinding, BucketsVM>(), BucketsNavigator {

    private val adapter = BucketsAdapter(object : AdapterNavigator<BucketItem> {
        override fun onItemClicked(item: BucketItem) {
            toast("Item ${item.name} clicked")
        }
    })

    companion object {
        fun getInstance(): BucketsFragment {
            return BucketsFragment()
        }
    }

    override fun getViewModels(): BucketsVM = ViewModelProviders.of(this).get(BucketsVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_buckets

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
    }

    private fun initUI() {
        viewModel.bucketsList.observe(this, Observer { list -> adapter.submitList(list) })
        rvBuckets.layoutManager = LinearLayoutManager(getParent())
        rvBuckets.adapter = adapter
    }

}