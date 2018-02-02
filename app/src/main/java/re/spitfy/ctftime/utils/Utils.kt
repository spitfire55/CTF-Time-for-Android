package re.spitfy.ctftime.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboardFrom(ctx : Context?, view: View?) {
    val imm = ctx?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Double.format(digits: Int) : String = java.lang.String.format("%.${digits}f", this)