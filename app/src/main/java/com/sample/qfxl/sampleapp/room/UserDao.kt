package com.sample.qfxl.sampleapp.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Flowable

/**
 * Created by qfxl on 2018/10/23.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM user")
    fun deleteAllUser()

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user")
    fun queryAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id=:id")
    fun querySpecifiedUser(id: Long): Flowable<User>

    @Query("DELETE FROM user WHERE id=:id")
    fun deleteSpecifiedUser(id: Long)
}