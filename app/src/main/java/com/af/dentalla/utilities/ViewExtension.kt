package com.af.dentalla.utilities

import android.view.View
import android.widget.Toast

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showToastShort(text: String) =
    Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()

fun View.showToastLong(text: String) = Toast.makeText(this.context, text, Toast.LENGTH_LONG).show()



