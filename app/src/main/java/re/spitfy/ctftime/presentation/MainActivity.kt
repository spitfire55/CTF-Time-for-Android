package re.spitfy.ctftime.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.design.widget.CoordinatorLayout

import kotlinx.android.synthetic.main.activity_main.*
import re.spitfy.ctftime.R
import re.spitfy.ctftime.presentation.common.BottomNavigationBehavior
import re.spitfy.ctftime.presentation.common.DrawerMenu
import re.spitfy.ctftime.utils.extensions.disableShiftMode
import re.spitfy.ctftime.utils.extensions.elevationForPostLollipop
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var drawerMenu: DrawerMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNavigation(savedInstanceState)
        drawerMenu.setup(navigation_mainActivity_drawer)
    }

    override fun onBackPressed() {
        //If drawer is open, close it and don't call super. Otherwise, call super
        if(drawerMenu.closeDrawerIfNeeded()) {
            super.onBackPressed()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupToolbar(BottomNavigationItem.forId(bottomNavigation_mainActivity.selectedItemId))
    }

    private fun setupToolbar(navigationItem: BottomNavigationItem) {
        toolbar_mainActivity.elevationForPostLollipop = if (navigationItem.isUseToolBarElevation) {
            resources.getDimensionPixelSize(R.dimen.toolbar_elevation).toFloat()
        } else {
            0f
        }
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setIcon(null)
            title = getString(navigationItem.titleRes)
        }
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        setBottomNavigationBehavior()
        bottomNavigation_mainActivity.disableShiftMode()
        bottomNavigation_mainActivity.setOnNavigationItemSelectedListener ({ item ->
            val navItem = BottomNavigationItem.forId(item.itemId)
            navItem.navigate(navigationController)
            true
        })
        if (savedInstanceState == null) {
            bottomNavigation_mainActivity.selectedItemId = R.id.navigation_home
        }
        bottomNavigation_mainActivity.setOnNavigationItemReselectedListener { item ->
            val navItem = BottomNavigationItem.forId(item.itemId)
            val fragment = supportFragmentManager.findFragmentByTag(navItem.name)
            if (fragment is BottomNavigationItem.OnReselectedListener) {
                fragment.onReselected()
            }
        }
    }

    private fun setBottomNavigationBehavior() {
        bottomNavigation_mainActivity.translationY = 0f
        (bottomNavigation_mainActivity.layoutParams as CoordinatorLayout.LayoutParams).behavior =
                BottomNavigationBehavior()
    }

    enum class BottomNavigationItem(
            @MenuRes val menuId: Int,
            @StringRes val titleRes: Int,
            // flag for if navbar destination has tabs or not.
            // if tabs in activity, set to false; if just toolbar, set to true.
            val isUseToolBarElevation: Boolean,
            val navigate: NavigationController.() -> Unit
    ) {
        HOME(R.id.navigation_home, R.string.navbar_home_title, false, {
            navigateHome()
        });

        // onReselected contains fragment-specific logic for when current nav item is reselected
        // (scroll to top, refresh, etc.)
        interface OnReselectedListener {
            fun onReselected()
        }

        companion object {
            fun forId(@IdRes id: Int): BottomNavigationItem {
                return values().first { it.menuId == id }
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)

        fun start(context: Context) {
            createIntent(context).let {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
        }
    }
}