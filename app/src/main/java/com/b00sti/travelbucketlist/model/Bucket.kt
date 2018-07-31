package com.b00sti.travelbucketlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by b00sti on 26.07.2018
 */
class Bucket {

    @Parcelize
    data class List(
            var name: String = "",
            var public: Boolean = true,
            var userId: String = "",
            var id: String = "",
            var to_dos: HashMap<String, String> = hashMapOf()
    ) : Parcelable

    @Parcelize
    data class ToDo(
            var name: String = "",
            var photoUrl: String = "",
            var type: Int = 1,
            var desc: String = "",
            var id: String = "")
        : Parcelable

}