package com.example.loansapp.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.loansapp.R

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

fun String.getResourcesLoanState(context: Context): String {
    return when (this) {
        "REGISTERED" -> context.resources.getString(R.string.loan_state_registered)

        "APPROVED" -> context.resources.getString(R.string.loan_state_approved)

        "REJECTED" -> context.resources.getString(R.string.loan_state_rejected)

        else -> context.resources.getString(R.string.loan_state_unknown)
    }
}