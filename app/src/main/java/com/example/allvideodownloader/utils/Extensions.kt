package com.example.allvideodownloader.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message : String) {
Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun View.visible(){
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
}
fun View.gone(){
    if (visibility != View.GONE){
        visibility = View.GONE
    }
}

fun TextView.setTextViewDrawableColor(color: Int) {
    this.compoundDrawables.forEach { drawable ->
        if (drawable != null) {
            drawable.colorFilter =
                PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }

    }
}



