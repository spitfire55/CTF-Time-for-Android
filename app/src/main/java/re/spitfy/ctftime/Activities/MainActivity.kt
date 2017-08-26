package re.spitfy.ctftime.Activities

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import re.spitfy.ctftime.Fragments.RankingsPagerFragment
import re.spitfy.ctftime.R

class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener
{

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit private var drawerToggle: ActionBarDrawerToggle
    lateinit private var toolbar: Toolbar
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbar_main)

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

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount == 0) {
            this.supportActionBar?.setTitle(R.string.app_name)
            this.title = getString(R.string.app_name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(getString(R.string.title), this.title)
    }

    private fun displayNavView(viewId: Int) {
        var title = getString(R.string.app_name) // default
        var fragment: Fragment? = null

        when (viewId) {
            R.id.nav_team_ranking -> {
                fragment = RankingsPagerFragment()
                title = getString(R.string.navbar_team_rankings)
            }
        }
        // gets fragment if it is already in the stack
        val oldFragment = supportFragmentManager.findFragmentByTag(title)
        if (fragment != null) {
            // check to see if fragment already in the stack
            if (oldFragment == null) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment, title)
                        .addToBackStack(title)
                        .commit()
            }
            else {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, oldFragment, title)
                        .commit()
            }
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
                R.string.navdrawer_close
        )
    }
}
