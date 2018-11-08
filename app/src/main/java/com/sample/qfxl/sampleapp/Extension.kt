package com.sample.qfxl.sampleapp

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2018/11/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun String.log() {
    Log.i("qfxl", this)
}