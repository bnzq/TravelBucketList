package com.b00sti.travelbucketlist.ui.dashboard

import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.databinding.FragmentDashboardBinding

/**
 * Created by b00sti on 27.07.2018
 */
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardVM>(), DashboardNavigator {

    companion object {
        fun getInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    override fun getViewModels(): DashboardVM = ViewModelProviders.of(this).get(DashboardVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_dashboard
    override fun initUI() {}
    override fun fetchInitialData() {}

}