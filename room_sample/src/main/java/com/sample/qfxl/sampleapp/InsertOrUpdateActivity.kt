package com.sample.qfxl.sampleapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.sample.qfxl.sampleapp.lifecycle.UserViewModel
import com.sample.qfxl.sampleapp.lifecycle.UserViewModelFactory
import com.sample.qfxl.sampleapp.room.AppDatabase
import com.sample.qfxl.sampleapp.room.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InsertOrUpdateActivity : AppCompatActivity() {
    private lateinit var userNameInput: EditText
    private lateinit var ageTv: TextView
    private lateinit var agePicker: NumberPicker
    private lateinit var userViewModel: UserViewModel
    private var userId: Long? = null
    private val disposables: CompositeDisposable = CompositeDisposable()

    companion object {
        const val PARAM_USER_ID = "paramUserId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_or_update)
        userNameInput = findViewById(R.id.et_name)
        ageTv = findViewById(R.id.tv_age)
        agePicker = findViewById(R.id.npk_age)
        agePicker.minValue = 0
        agePicker.maxValue = 100

        userViewModel = ViewModelProviders.of(this, UserViewModelFactory(AppDatabase.getInstance(this).userDao()))
                .get(UserViewModel::class.java)

        userId = intent?.extras?.getLong(PARAM_USER_ID)
        if (null != userId) {
            val dis = userViewModel.querySpecifiedUser(userId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        userNameInput.setText(it.name)
                        agePicker.value = it.age
                    }
            disposables.add(dis)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_save -> {
                saveOrUpdate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveOrUpdate() {
        val userName = userNameInput.text.toString()
        val age = agePicker.value
        val disposable: Disposable?
        if (null != userId) {
            disposable = userViewModel.updateUser(User(userName, age).apply {
                id = userId
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        finish()
                    }, {
                        "插入数据失败 ${it.message}".log()
                    })
        } else {
            disposable = userViewModel.insertUser(User(userName, age))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        finish()
                    }, {
                        "插入数据失败 ${it.message}".log()
                    })
        }
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
