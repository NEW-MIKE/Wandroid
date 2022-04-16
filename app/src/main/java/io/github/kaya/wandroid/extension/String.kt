package io.github.kaya.wandroid.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import io.github.kaya.wandroid.App
import io.github.kaya.wandroid.BuildConfig

fun String.logE() {
    if (BuildConfig.DEBUG) {
        Log.e("Wandroid", this)
    }
}

fun String.logV() {
    if (BuildConfig.DEBUG) {
        Log.v("Wandroid", this)
    }
}

fun String.changeExt(ext: String): String {
    return substring(0, lastIndexOf(".") + 1) + ext
}

fun String.copy(context: Context) {
    val cm =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val data = ClipData.newPlainText("", this)
    cm.primaryClip = data
    Toast.makeText(context.applicationContext, "复制成功", Toast.LENGTH_SHORT)
        .show()
}

fun String.toast() {
    Toast.makeText(App.instance, this, Toast.LENGTH_SHORT).show()
}

fun String.openBrowser(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
    context.startActivity(intent)
}