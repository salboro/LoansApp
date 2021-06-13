package com.example.loansapp.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.closeKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Int.isEven(): Boolean =
    this % 2 == 0

fun Resources.Theme.getColor(colorAttr: Int): Int {
    val typedValue = TypedValue()
    this.resolveAttribute(colorAttr, typedValue, true)
    return typedValue.data
}