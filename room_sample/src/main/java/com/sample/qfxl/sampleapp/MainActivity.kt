package com.sample.qfxl.sampleapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.sample.qfxl.sampleapp.adapter.UserAdapter
import com.sample.qfxl.sampleapp.lifecycle.UserViewModel
import com.sample.qfxl.sampleapp.lifecycle.UserViewModelFactory
import com.sample.qfxl.sampleapp.room.User
import com.sample.qfxl.sampleapp.room.AppDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRv: RecyclerView
    private lateinit var addFloatingButton: FloatingActionButton
    private lateinit var userViewModel: UserViewModel
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var selectedUserId = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRv = findViewById(R.id.rv_content)
        userRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val adapter = UserAdapter()
        userRv.adapter = adapter

        addFloatingButton = findViewById(R.id.btn_add)
        addFloatingButton.setOnClickListener {
            startActivity(Intent(MainActivity@ this, InsertOrUpdateActivity::class.java))
        }

        userViewModel = ViewModelProviders.of(this, UserViewModelFactory(AppDatabase.getInstance(this).userDao()))
                .get(UserViewModel::class.java)

        userViewModel.queryAllUsers().observe(this, Observer {
            adapter.setUserList(it)
            adapter.setOnTapClickListener({ _, position ->
                val userId = it?.get(position)?.id
                startActivity(Intent(MainActivity@ this, InsertOrUpdateActivity::class.java).also {
                    it.putExtra(InsertOrUpdateActivity.PARAM_USER_ID, userId)
                })
            }, { _, position ->
                selectedUserId = it!![position].id!!
                false
            })
        })

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_more, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_delete -> {
                val deleteAllDis = userViewModel.deleteAllUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {

                        }
                disposables.add(deleteAllDis)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (selectedUserId > -1) {
            val deleteDis = userViewModel.deleteSpecifiedUser(selectedUserId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {

                    }
            disposables.add(deleteDis)
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}


