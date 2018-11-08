package com.sample.qfxl.sampleapp.lifecycle

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sample.qfxl.sampleapp.room.UserDao

/**
 * Created by qfxl on 2018/10/23.
 */
class UserViewModelFactory(private val dao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dao) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }
}