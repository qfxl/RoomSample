package com.sample.qfxl.sampleapp.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/11/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class AppRoomMigration(startVersion: Int, endVersion: Int) : Migration(startVersion, endVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //版本1迁移到版本2
        if (startVersion == 1 && endVersion == 2) {
            database.execSQL("ALTER TABLE user ADD COLUMN gender Int")
        }
    }
}