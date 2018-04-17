package re.spitfy.ctftime.presentation.common

import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.View
import re.spitfy.ctftime.R
import re.spitfy.ctftime.presentation.MainActivity
import re.spitfy.ctftime.presentation.NavigationController
import javax.inject.Inject
import kotlin.reflect.KClass

class DrawerMenu @Inject constructor(
        private val activity: AppCompatActivity,
        private val navigationController: NavigationController
) {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var currentNavigationItem: DrawerNavigationItem

    fun setup(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { item ->
            DrawerNavigationItem.values().first { it.menuId == item.itemId }.apply {
                if (this != currentNavigationItem) {
                    navigate(navigationController)
                }
            }
            drawerLayout.closeDrawers()
            false
        }

        currentNavigationItem = DrawerNavigationItem
                .values()
                .firstOrNull { activity::class == it.activityClass }
                ?.also {
                    navigationView.setCheckedItem(it.menuId)
                }
                ?: DrawerNavigationItem.OTHER
    }

    fun closeDrawerIfNeeded(): Boolean {
        return if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
            false
        } else {
            true
        }
    }


    enum class DrawerNavigationItem(
            @IdRes val menuId: Int,
            val activityClass: KClass<*>,
            val navigate: NavigationController.() -> Unit
    ) {
        HOME(R.id.drawer_home, MainActivity::class, {
            navigateToMainActivity()
        }),

        OTHER(0, Unit::class, {
            //DO NOTHING
        })
    }

}