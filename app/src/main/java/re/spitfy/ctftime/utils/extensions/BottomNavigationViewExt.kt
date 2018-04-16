package re.spitfy.ctftime.utils.extensions

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.widget.BottomNavigationView
import android.util.Log

@SuppressLint("RestrictedApi") // I know what I'm doing...
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationView
    try {
        val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e(this::class.java.simpleName, "Unable to get shift mode field", e)
    } catch (e: IllegalStateException) {
        Log.e(this::class.java.simpleName, "Unable to change value of shift mode", e)
    }
}