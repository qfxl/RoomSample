package com.sample.qfxl.sampleapp.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by qfxl on 2018/10/23.
 */
class ListConverter {

    @TypeConverter
    fun listToString(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(s: String): ArrayList<String> {
        val token = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(s, token)
    }
}