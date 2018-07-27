package com.b00sti.travelbucketlist.ui.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.ui.dashboard.DashboardFragment
import com.b00sti.travelbucketlist.ui.my_all_lists.MyAllListsFragment
import com.b00sti.travelbucketlist.ui.public_all_lists.PublicAllListsFragment

/**
 * Created by b00sti on 08.02.2018
 */
class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val fragments = ArrayList<BaseFragment<*, *>>()

    init {
        fragments.add(MyAllListsFragment.getInstance())
        fragments.add(DashboardFragment.getInstance())
        fragments.add(PublicAllListsFragment.getInstance())
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount(): Int = fragments.size
}