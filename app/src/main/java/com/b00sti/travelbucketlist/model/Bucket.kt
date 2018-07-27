package com.b00sti.travelbucketlist.model

/**
 * Created by b00sti on 26.07.2018
 */
class Bucket {
    data class BucketList(var name: String = "", var public: Boolean = true, var userId: String = "", var id: String = "")
    data class BucketToDo(var name: String = "", var photoUrl: String = "", var type: Int = 1, var desc: String = "", var id: String = "")
}