package re.spitfy.ctftime.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {

    companion object {
        fun hideKeyboardFrom(ctx : Context?, view: View?) {
            val imm = ctx?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }
}