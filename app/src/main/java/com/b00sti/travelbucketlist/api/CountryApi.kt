package com.b00sti.travelbucketlist.api

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by b00sti on 19.06.2018
 */
interface CountryApi {

    @GET("all")
    fun getCountries(): Observable<List<Country>>

}