package com.b00sti.travelbucketlist.ui.public_bucket_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentPublicBucketListBinding
import com.b00sti.travelbucketlist.model.Bucket
import com.b00sti.travelbucketlist.utils.finish
import com.b00sti.travelbucketlist.utils.toast
import kotlinx.android.synthetic.main.fragment_my_all_lists.*

/**
 * Created by b00sti on 27.07.2018
 */
class PublicBucketListFragment : BaseFragment<FragmentPublicBucketListBinding, PublicBucketListVM>(), PublicBucketListNavigator {

    companion object {
        fun getInstance(bundle: Bundle): PublicBucketListFragment {
            return PublicBucketListFragment().apply { arguments = bundle }
        }

        fun prepareBundle(item: Bucket.List): Bundle {
            return Bundle().apply {
                putInt(SingleActivity.KIND_OF_FRAGMENT_INTENT, SingleActivity.PUBLIC_BUCKET_LIST_FRAGMENT)
                putParcelable(SingleActivity.BUNDLE_INTENT, item)
            }
        }
    }

    private val adapter = BucketToDosAdapter(object : BucketToDosNavigator {
        override fun onItemClicked(item: Bucket.ToDo) {
            toast("Item ${item.name} clicked")
        }
    })

    override fun getViewModels(): PublicBucketListVM = ViewModelProviders.of(this).get(PublicBucketListVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_public_bucket_list

    override fun initUI() {
        viewModel.setNavigator(this)
        viewModel.listOfBuckets.observe(this, Observer { adapter.submitList(it) })
        rvBuckets.layoutManager = LinearLayoutManager(getParent())
        rvBuckets.adapter = adapter
    }

    override fun fetchInitialData() = getFromBundle<Bucket.List>()?.let {
        viewModel.bucketList = it
        viewModel.getBucketList()
    }

    override fun onCopyCompleted() = finish()

}