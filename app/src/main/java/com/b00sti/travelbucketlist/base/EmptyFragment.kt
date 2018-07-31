package com.b00sti.travelbucketlist.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_empty.*

/**
 * Created by b00sti on 08.02.2018
 */
class EmptyFragment : BaseFragment<ActivityMainBinding, EmptyViewModel>(), EmptyNavigator {

    override fun getViewModels(): EmptyViewModel = ViewModelProviders.of(this).get(EmptyViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_empty
    override fun initUI() = viewModel.setNavigator(this)
    override fun fetchInitialData() = getFromBundle<String>("msg")?.let {
        tvEmptyMsg.text = it
    }

    companion object {
        fun getInstance(title: String): EmptyFragment {
            val fragment = EmptyFragment()
            val bundle = Bundle()
            bundle.putString("msg", title)
            fragment.arguments = bundle
            return fragment
        }
    }

}