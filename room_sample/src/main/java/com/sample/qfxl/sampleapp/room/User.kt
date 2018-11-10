package com.sample.qfxl.sampleapp.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by qfxl on 2018/10/23.
 */
@Entity(tableName = "user")
data class User(
        val name: String,
        val age: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @Ignore
    var list: ArrayList<String>? = null

    var createDate = Date()

    var gender: Int? = null
}