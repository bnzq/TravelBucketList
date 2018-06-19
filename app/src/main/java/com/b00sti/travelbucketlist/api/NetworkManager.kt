package com.b00sti.travelbucketlist.api

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by b00sti on 19.06.2018
 */
object NetworkManager {

    const val BASE_API = "https://restcountries.eu/rest/v2/"

    fun getCountriesFromApi(): Observable<List<Country>> {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val countryApi = retrofit.create(CountryApi::class.java)
        return countryApi.getCountries()
    }

}