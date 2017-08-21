package re.spitfy.ctftime.Activities

import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import re.spitfy.ctftime.R
import re.spitfy.ctftime.Fragments.TeamRankingsFragment

class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener
{

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit private var drawerToggle: ActionBarDrawerToggle
    lateinit private var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbar_main)

        toolbar = findViewById(R.id.toolbar)
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
        drawerLayout.closeDrawer(GravityCompat.START)
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
        }
    }

    private fun displayNavView(viewId: Int) {
        var title = getString(R.string.app_name) // default
        var fragment: Fragment? = null

        when (viewId) {
            R.id.nav_team_ranking -> {
                fragment = TeamRankingsFragment()
                title = "Team Rankings"
            }
        }

        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack("main")
                    .commit()
            this.supportActionBar?.title = title
            drawerLayout.closeDrawers()
        }
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
