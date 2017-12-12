package re.spitfy.ctftime.activities

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import re.spitfy.ctftime.R
import re.spitfy.ctftime.fragments.TeamProfileFragment
import re.spitfy.ctftime.fragments.TeamRankingsFragment


class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener
{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar
    private var title: String? = null
    private var userIsInteracting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        if(savedInstanceState != null) {
            val savedTitle = savedInstanceState.getString(getString(R.string.title))
            this.title = savedTitle
            toolbar.title = savedTitle
        }
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = setupDrawerToggle()
        navView = findViewById(R.id.navView)
        navView.setNavigationItemSelectedListener({ menuItem ->
            displayNavView(menuItem.itemId)
            true
        })
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nested_scroll_view)
        nestedScrollView.isFillViewport = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        // Handle navigation view item clicks here.
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(getString(R.string.title), this.title)
    }

    private fun displayNavView(viewId: Int) {
        var title = getString(R.string.app_name) // default
        var tag = getString(R.string.app_name)
        var fragment: Fragment? = null

        when (viewId) {
            R.id.nav_team_ranking -> {
                fragment = TeamRankingsFragment.newInstance("2017", 0)
                title = getString(R.string.toolbar_team_rankings)
                tag = "2017-0"
            }
            R.id.nav_team_profile -> {
                fragment = TeamProfileFragment.newInstance(8327)
                title = getString(R.string.toolbar_team_profiles)
                tag = title
            }
        }
        // gets fragment if it is already in the stack
        if (fragment != null && this.title != title) {
            // check to see if fragment already in the stack
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainFrame, fragment, tag)
                    .commit()
            this.supportActionBar?.title = title
            this.title = title
        }
        drawerLayout.closeDrawers()
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {

        return ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navdrawer_open,
                R.string.navdrawer_close)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        userIsInteracting = true
    }
}
