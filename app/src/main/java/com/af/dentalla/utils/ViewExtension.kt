package com.af.dentalla.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.showToastShort(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.showToastLong(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()



