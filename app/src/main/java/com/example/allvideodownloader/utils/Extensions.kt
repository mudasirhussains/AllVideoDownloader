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

fun Context.colorList(id: Int): ColorStateList {
    return ColorStateList.valueOf(ContextCompat.getColor(this, id))
}

fun Context.showToast(message : String) {
Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

//fun customizedRequiredText(context: Context, text: String): CharSequence {
//    val customFont = ResourcesCompat.getFont(context, R.font.montserrat_bold)
//    return Spanny().append(
//        text,
//        ForegroundColorSpan(ContextCompat.getColor(context, R.color.blackColor))
//    ).appendWithFont(
//        " *", customFont!!,
//        ForegroundColorSpan(ContextCompat.getColor(context, R.color.primaryColor))
//    ).build()
//}

fun TextView.setTextColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, color))
}

fun TextView.setTextViewDrawableColor(color: Int) {
    this.compoundDrawables.forEach { drawable ->
        if (drawable != null) {
            drawable.colorFilter =
                PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }

    }
}

fun Calendar.getCurrentDateString(): String {
    val currentDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return currentDateFormat.format(this.time)
}


fun String.toDoubleOrZero(): Double {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}



