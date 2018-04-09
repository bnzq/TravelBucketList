package com.b00sti.travelbucketlist.ui.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.ui.countries.CountriesFragment
import com.b00sti.travelbucketlist.utils.EmptyFragment

/**
 * Created by b00sti on 08.02.2018
 */
class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val fragments = ArrayList<BaseFragment<*, *>>()

    init {
        fragments.add(EmptyFragment.getInstance("1"))
        fragments.add(EmptyFragment.getInstance("2"))
        fragments.add(CountriesFragment.getInstance())
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount(): Int = fragments.size
}