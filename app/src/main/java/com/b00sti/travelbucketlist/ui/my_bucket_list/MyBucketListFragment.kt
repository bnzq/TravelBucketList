package com.b00sti.travelbucketlist.ui.my_bucket_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentMyBucketListBinding
import com.b00sti.travelbucketlist.model.Bucket
import com.b00sti.travelbucketlist.ui.public_bucket_list.BucketToDosAdapter
import com.b00sti.travelbucketlist.ui.public_bucket_list.BucketToDosNavigator
import com.b00sti.travelbucketlist.utils.toast
import kotlinx.android.synthetic.main.fragment_my_bucket_list.*

/**
 * Created by b00sti on 27.07.2018
 */
class MyBucketListFragment : BaseFragment<FragmentMyBucketListBinding, MyBucketListVM>(), MyBucketListNavigator {

    companion object {
        fun getInstance(bundle: Bundle): MyBucketListFragment {
            return MyBucketListFragment().apply { arguments = bundle }
        }

        fun prepareBundle(item: Bucket.List): Bundle {
            return Bundle().apply {
                putInt(SingleActivity.KIND_OF_FRAGMENT_INTENT, SingleActivity.MY_BUCKET_LIST_FRAGMENT)
                putParcelable(SingleActivity.BUNDLE_INTENT, item)
            }
        }
    }

    private val adapter = BucketToDosAdapter(object : BucketToDosNavigator {
        override fun onItemClicked(item: Bucket.ToDo) {
            toast("Item ${item.name} clicked")
        }
    })

    override fun getViewModels(): MyBucketListVM = ViewModelProviders.of(this).get(MyBucketListVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_my_bucket_list

    override fun initUI() {
        viewModel.setNavigator(this)
        viewModel.listOfBuckets.observe(this, Observer { adapter.submitList(it) })
        rvToDos.layoutManager = LinearLayoutManager(getParent())
        rvToDos.adapter = adapter
    }

    override fun fetchInitialData() = getFromBundle<Bucket.List>()?.let {
        viewModel.bucketList = it
        viewModel.getBucketList()
    }

}