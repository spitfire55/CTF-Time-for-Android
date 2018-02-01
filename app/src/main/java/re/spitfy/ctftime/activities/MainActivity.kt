package re.spitfy.ctftime.activities

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import org.jetbrains.anko.toast
import re.spitfy.ctftime.R
import re.spitfy.ctftime.fragments.HomeFragment
import re.spitfy.ctftime.fragments.TeamProfileFragment
import re.spitfy.ctftime.fragments.TeamRankingsFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: android.support.v7.widget.Toolbar
    private lateinit var currentFragment : Fragment
    private var secondBackClickFlag = false
    private val backPressHandler = Handler()
    private val backPressRunnable = Runnable { secondBackClickFlag = false }
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        val mainView = findViewById<DrawerLayout>(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)

        if(savedInstanceState != null) {
            val savedTitle = savedInstanceState.getString(getString(R.string.title))
            this.title = savedTitle
            toolbar.title = savedTitle
        }

        drawerToggle = setupDrawerToggle()
        navView = findViewById(R.id.mainNav)
        drawerLayout.addDrawerListener(drawerToggle)
        navView.setNavigationItemSelectedListener {
            displayNavView(it.itemId)
            true
        }
        setSupportActionBar(toolbar)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, android.R.color.white)
            mainView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        currentFragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, this.title)
        } else {
            acquireFragment(R.id.nav_home)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(getString(R.string.title), this.title)
        supportFragmentManager.putFragment(outState, this.title, currentFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayNavView(item.itemId)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressHandler.removeCallbacks(backPressRunnable)
    }

    override fun onBackPressed() {
        if (secondBackClickFlag || supportFragmentManager.backStackEntryCount != 0) {
            super.onBackPressed()
            return
        }
        secondBackClickFlag = true
        toast("Press back button again to exit")
        backPressHandler.postDelayed(backPressRunnable, 2000)
    }

    private fun acquireFragment(viewId: Int) : android.support.v4.app.Fragment {
        var title = getString(R.string.app_name) // default
        var fragment : Fragment? = null

        when (viewId) {
            R.id.nav_home -> {
                fragment = supportFragmentManager.findFragmentByTag(getString(R.string.toolbar_home)) ?: HomeFragment()
                title = getString(R.string.app_name)
            }
            R.id.nav_team_ranking -> {
                fragment = TeamRankingsFragment.newInstance(getString(R.string.current_year))
                title = getString(R.string.toolbar_team_rankings)
            }
            R.id.nav_team_profile -> {
                fragment = TeamProfileFragment.newInstance(null)
                title = getString(R.string.toolbar_team_profiles)
            }
        }
        if (title == this.title) {
            return currentFragment
        }
        return fragment ?: Fragment()
    }

    private fun displayNavView(viewId: Int) {
        val tempFragment = acquireFragment(viewId)
        if (tempFragment != currentFragment) {
            // Clear stack of current menu item fragments (all Rankings, all Teams, etc.) but preserve Home
            supportFragmentManager.popBackStack(title, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            currentFragment = tempFragment
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, currentFragment, title)
                    .addToBackStack(this.title)
                    .commit()
        }
        drawerLayout.closeDrawers()
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {

        return ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navdrawer_open,
                R.string.navdrawer_close
        )
    }
}
