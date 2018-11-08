package com.sample.qfxl.sampleapp.room

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by qfxl on 2018/10/23.
 */
class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}