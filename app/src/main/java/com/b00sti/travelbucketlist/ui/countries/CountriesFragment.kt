package com.b00sti.travelbucketlist.ui.countries

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.utils.adapter.CONTINENTS
import com.b00sti.travelbucketlist.utils.adapter.CountryAdapter
import com.b00sti.travelbucketlist.utils.adapter.CountryItem
import com.b00sti.travelbucketlist.utils.adapter.CountryNavigator
import com.b00sti.travelbucketlist.utils.toast
import kotlinx.android.synthetic.main.fragment_countries.*

/**
 * Created by b00sti on 12.03.2018
 */
class CountriesFragment : BaseFragment<FragmentCountriesBinding, CountriesVM>(), CountriesNavigator {

    companion object {
        fun getInstance(): CountriesFragment {
            return CountriesFragment()
        }
    }

    override fun getViewModels(): CountriesVM = ViewModelProviders.of(this).get(CountriesVM::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_countries

    override fun onResume() {
        super.onResume()
        viewModel.setNavigator(this)
        val adapter = CountryAdapter(object : CountryNavigator {

            override fun onItemClicked(countryItem: CountryItem) {
                toast("Item ${countryItem.name} clicked")
            }

            override fun onDeleteClicked(countryItem: CountryItem) {
                viewModel.countriesList.value?.remove(countryItem)
                toast("Item ${countryItem.name} removed")
            }

        })
        viewModel.countriesList.observe(this, Observer { list -> adapter.submitList(list) })
        val items = arrayListOf(CountryItem("Poland", CONTINENTS.EUROPE, true, ""),
                CountryItem("France", CONTINENTS.EUROPE, false, ""),
                CountryItem("Spain", CONTINENTS.EUROPE, true, ""))
        viewModel.countriesList.postValue(items)
        rvCountries.adapter = adapter
    }

}