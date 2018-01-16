package re.spitfy.ctftime.activities

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import re.spitfy.ctftime.R
import re.spitfy.ctftime.fragments.TeamProfileFragment
import re.spitfy.ctftime.fragments.TeamRankingsFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: android.support.v7.widget.Toolbar
    private var title: String? = null
    private var userIsInteracting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
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
                fragment = TeamRankingsFragment.newInstance("2017")
                title = getString(R.string.toolbar_team_rankings)
                tag = "2017"
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
                    .replace(R.id.container, fragment, tag)
                    .commit()
            this.actionBar?.title = title
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
                R.string.navdrawer_close
        )
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        userIsInteracting = true
    }
}
