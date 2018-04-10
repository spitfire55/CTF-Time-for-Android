package re.spitfy.ctftime.presentation

import android.os.Bundle
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import re.spitfy.ctftime.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private val mainActivityViewModel by lazy { getViewModel(MainActivityViewModel::class.java)}
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val background by lazy {ContextCompat.getColor(this, R.color.colorSecondaryLight)}

    override fun onCreate(savedInstanceStzzzzzzate: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation(savedInstanceState)
    }

    override fun setupToolbar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        //TODO: Setup Bottom Navigation
    }

    enum class BottomNavigationItem(
            @MenuRes val menuId: Int,
            @StringRes val titleRes: Int?,
            // flag for if navbar destination has
            val isUseToolBarElevation: Boolean
            //TODO: Implement Navigation Controller
    ) {
        RANKINGS(R.id.navigation_rankings, R.string.navbar_rankings_title, false)
    }

}