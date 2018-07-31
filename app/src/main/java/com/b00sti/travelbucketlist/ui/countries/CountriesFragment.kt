package com.b00sti.travelbucketlist.ui.countries

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.b00sti.travelbucketlist.BR
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.base.BaseFragment
import com.b00sti.travelbucketlist.base.SingleActivity
import com.b00sti.travelbucketlist.databinding.FragmentCountriesBinding
import com.b00sti.travelbucketlist.utils.ScreenRouter
import com.b00sti.travelbucketlist.utils.adapter.CountryAdapter
import com.b00sti.travelbucketlist.utils.adapter.CountryItem
import com.b00sti.travelbucketlist.utils.adapter.CountryNavigator
import com.b00sti.travelbucketlist.utils.toast

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
        //initUI()
        //addAllCountries()
        getAllCountries()

    }

    override fun initUI() {}
    override fun fetchInitialData() {}

    private fun getAllCountries() {
/*        val list: MutableList<CountryItem> = mutableListOf()
        RxFbDatabase.getBucketList(Bucket.List("", true, "", "-LILHhlNfSTaO_sAPIyf")).subscribeBy { arrayList ->
            val listNewItems: MutableList<CountryItem> = mutableListOf()
            arrayList.mapTo(listNewItems, transform = {
                CountryItem(it.name, CONTINENTS.values()[it.type], false, it.photoUrl, it.desc)
            })
            val updateList: MutableList<CountryItem> = mutableListOf()
            updateList.addAll(list)
            list.addAll(listNewItems)
            updateList.addAll(listNewItems)
            adapter.submitList(updateList)
        }*/
    }

    /* private fun addAllCountries() {
         var bucketList = Bucket.List("All countries", true, RxFbAuth.getUserId(), "")
         RxFbDatabase.addNewBucketList(bucketList).subscribe({
             NetworkManager.getCountriesFromApi().compose(RxUtils.applyObservableSchedulers()).subscribeBy(onNext = {
                 it.forEach {
                     var bucketItem = Bucket.ToDo(
                             it.name,
                             "https://raw.githubusercontent.com/hjnilsson/country-flags/master/png250px/" + it.alpha2Code.toLowerCase() + ".png",
                             getCountryType(it),
                             getCountryDesc(it))
                     RxFbDatabase.addNewBucketToDo(bucketItem).subscribe({
                         RxFbDatabase.assignToDoToBucketList(bucketItem, bucketList).subscribe({})
                     })
                 }
             })
         })
     }

     private fun getCountryType(country: Country): Int {
         var result = 0
         if (country.region.equals("Europe")) {
             result = CONTINENTS.EUROPE.ordinal
         } else if (country.region.equals("Africa")) {
             result = CONTINENTS.AFRICA.ordinal
         } else if (country.region.equals("Asia")) {
             result = CONTINENTS.ASIA.ordinal
         } else if (country.region.equals("Oceania")) {
             result = CONTINENTS.OCEANIA.ordinal
         } else if (country.region.equals("Americas")) {
             result = if (country.subregion.equals("South America")) {
                 CONTINENTS.NORTH_AMERICA.ordinal
             } else {
                 CONTINENTS.SOUTH_AMERICA.ordinal
             }
         }
         return result
     }

     private fun getCountryDesc(country: Country): String {
         var result = ""
         val symbols = DecimalFormatSymbols()
         symbols.groupingSeparator = ' '
         val df = DecimalFormat("###,###,###,###", symbols)
         result += "Codes: " + country.alpha2Code + " / " + country.alpha3Code
         result += "\nRegion: " + country.subregion
         result += "\nNative name: " + country.nativeName
         result += "\nCapital: " + country.capital
         result += "\nPopulation: " + df.format(country.population)
         result += try {
             "\nArea: " + df.format(country.area)
         } catch (e: Exception) {
             "\nArea: unknown"
         }
         return result
     }

     private fun initUI() {
         viewModel.countriesList.observe(this, Observer { list -> adapter.submitList(list) })
         rvCountries.layoutManager = LinearLayoutManager(getParent())
         rvCountries.adapter = adapter
     }
 */
    override fun onRefreshCompleted() {
        ScreenRouter.startWithoutParams(getBase(), SingleActivity::class)
    }
}