package com.b00sti.travelbucketlist.utils

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.ActivityMainBinding
import com.b00sti.travelbucketlist.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_empty.*

/**
 * Created by b00sti on 08.02.2018
 */
class EmptyFragment : BaseFragment<ActivityMainBinding, MainViewModel>() {

    override fun getViewModels(): MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_empty

    companion object {
        fun getInstance(title: String): EmptyFragment {
            val fragment = EmptyFragment()
            val bundle = Bundle()
            bundle.putString("msg", title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            tvEmptyMsg.text = it.getString("msg")
        }
    }

}