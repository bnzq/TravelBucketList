package com.b00sti.travelbucketlist.ui.public_bucket_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentPublicBucketListBinding
import com.b00sti.travelbucketlist.model.Bucket
import com.b00sti.travelbucketlist.utils.toast
import kotlinx.android.synthetic.main.fragment_public_bucket_list.*

/**
 * Created by b00sti on 27.07.2018
 */
class PublicBucketListFragment : BaseFragment<FragmentPublicBucketListBinding, PublicBucketListVM>(), PublicBucketListNavigator {

    companion object {
        fun getInstance(): PublicBucketListFragment {
            return PublicBucketListFragment()
        }
    }

    override fun getViewModels(): PublicBucketListVM = ViewModelProviders.of(this).get(PublicBucketListVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_public_bucket_list

    private val adapter = BucketToDosAdapter(object : BucketToDosNavigator {
        override fun onItemClicked(item: Bucket.BucketToDo) {
            toast("Item ${item.name} clicked")
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
        viewModel.getBucketList(Bucket.BucketList("", true, "", "-LILHhlNfSTaO_sAPIyf"))
    }

    private fun initUI() {
        viewModel.listOfBuckets.observe(this, Observer { adapter.submitList(it) })
        rvToDos.layoutManager = LinearLayoutManager(getParent())
        rvToDos.adapter = adapter
    }

}