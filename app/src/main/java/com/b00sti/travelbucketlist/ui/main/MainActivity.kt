package com.b00sti.travelbucketlist.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseActivity
import com.b00sti.travelbucketlist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    val mViewPagerAdapter = MainPagerAdapter(supportFragmentManager)

    override fun getViewModels(): MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModels().setNavigator(this)
        vgBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        vpMainNavigation.adapter = mViewPagerAdapter
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                vpMainNavigation.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                vpMainNavigation.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                vpMainNavigation.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackArrowClick() {
    }

    override fun onSettingsClicked() {
    }
}
