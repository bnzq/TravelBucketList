package com.b00sti.travelbucketlist.ui.countries

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.api.NetworkManager
import com.b00sti.travelbucketlist.api.RxFirebaseDatabase
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentCountriesBinding
import com.b00sti.travelbucketlist.utils.RxUtils
import com.b00sti.travelbucketlist.utils.ScreenRouter
import com.b00sti.travelbucketlist.utils.adapter.CONTINENTS
import com.b00sti.travelbucketlist.utils.adapter.CountryAdapter
import com.b00sti.travelbucketlist.utils.adapter.CountryItem
import com.b00sti.travelbucketlist.utils.adapter.CountryNavigator
import com.b00sti.travelbucketlist.utils.toast
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_countries.*

/**
 * Created by b00sti on 12.03.2018
 */
class CountriesFragment : BaseFragment<FragmentCountriesBinding, CountriesVM>(), CountriesNavigator {

    private val adapter = CountryAdapter(object : CountryNavigator {
        override fun onItemClicked(item: CountryItem) {
            toast("Item ${item.name} clicked")
        }
/*
        override fun onItemClicked(countryItem: CountryItem) {
            toast("Item ${countryItem.name} clicked")
        }*/

        override fun onDeleteClicked(countryItem: CountryItem) {
            //viewModel.countriesList.value?.remove(countryItem)
            toast("Item ${countryItem.name} removed")
            viewModel.removeItem(countryItem)
        }

    })

    companion object {
        fun getInstance(): CountriesFragment {
            return CountriesFragment()
        }
    }

    override fun getViewModels(): CountriesVM = ViewModelProviders.of(this).get(CountriesVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_countries

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
        var bucketList = RxFirebaseDatabase.BucketList("All countries", false, "userID")
        RxFirebaseDatabase.addNewBucketList(bucketList).subscribe({
            NetworkManager.getCountriesFromApi().compose(RxUtils.applyObservableSchedulers()).subscribeBy(onNext = {
                var list: MutableList<CountryItem> = mutableListOf()
                it.mapTo(list, transform = { CountryItem(it.name, CONTINENTS.EUROPE, false, it.alpha2Code) })
                list.forEach {
                    var bucketItem = RxFirebaseDatabase.BucketItem(it.name,
                            "https://raw.githubusercontent.com/hjnilsson/country-flags/master/png250px/" + it.photoUri.toLowerCase() + ".png",
                            it.continent.ordinal,
                            "")
                    RxFirebaseDatabase.addNewItem(bucketItem).subscribe({
                        //RxFirebaseDatabase.assignToBucketList(bucketItem, bucketList).subscribe({})
                    })
                }


                adapter.submitList(list)
            })
        })
    }

    private fun initUI() {
        viewModel.countriesList.observe(this, Observer { list -> adapter.submitList(list) })
        rvCountries.layoutManager = LinearLayoutManager(getParent())
        rvCountries.adapter = adapter
    }

    override fun onRefreshCompleted() {
        ScreenRouter.startWithoutParams(getBase(), SingleActivity::class)
    }
}