package com.sample.qfxl.sampleapp.lifecycle

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.sample.qfxl.sampleapp.room.User
import com.sample.qfxl.sampleapp.room.UserDao
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by qfxl on 2018/10/23.
 */
class UserViewModel(private val dao: UserDao) : ViewModel() {

    fun queryAllUsers(): LiveData<List<User>> = dao.queryAllUsers()

    fun querySpecifiedUser(id: Long): Flowable<User> = dao.querySpecifiedUser(id)

    fun insertUser(user: User): Completable = Completable.fromAction { dao.addUser(user) }

    fun updateUser(user: User): Completable = Completable.fromAction { dao.updateUser(user) }

    fun deleteSpecifiedUser(id: Long): Completable = Completable.fromAction { dao.deleteSpecifiedUser(id) }

    fun deleteAllUser(): Completable = Completable.fromAction { dao.deleteAllUser() }
}