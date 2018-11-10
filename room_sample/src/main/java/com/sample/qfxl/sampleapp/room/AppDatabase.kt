package com.sample.qfxl.sampleapp.room

import android.arch.persistence.room.*
import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by qfxl on 2018/10/23.
 */
@Database(entities = [User::class], version = 2, exportSchema = false)
@TypeConverters(ListConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): AppDatabase {
            val dbDir = File(Environment.getExternalStorageDirectory(), "GoogleRoomDatabase")
            if (dbDir.exists()) {
                dbDir.mkdir()
            }

            val dbFile = File(dbDir, "Sample.db")
            return Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, dbFile.absolutePath)
                    .addMigrations(AppRoomMigration(1, 2))
                    .build()
        }

    }

    abstract fun userDao(): UserDao
}