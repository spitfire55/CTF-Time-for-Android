package re.spitfy.ctftime.presentation

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import re.spitfy.ctftime.R
import re.spitfy.ctftime.presentation.home.HomeFragment
import javax.inject.Inject

class NavigationController @Inject constructor(private val activity: AppCompatActivity) {
    //FrameLayout in each activity that we replace with fragments will have same id (content) so
    // that this controller doesn't have to create a bunch of layout-specific variables
    private val containerId: Int = R.id.content
    private val fragmentManager = activity.supportFragmentManager

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment, fragment.tag)
                .commit()
    }

    fun navigateHome() {
        replaceFragment(HomeFragment.newInstance())
    }

    fun navigateToMainActivity() {
        MainActivity.start(activity)
    }
}