package re.spitfy.ctftime.presentation

import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import re.spitfy.ctftime.R
import re.spitfy.ctftime.view.decoration.StandingsItemDecoration
import re.spitfy.ctftime.view.item.HeaderItem
import re.spitfy.ctftime.view.item.StandingsCarouselItem
import re.spitfy.ctftime.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val background by lazy {ContextCompat.getColor(this, R.color.colorSecondaryLight)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = getViewModel(MainActivityViewModel::class.java)
        populateAdapter()
    }

    override fun setupToolbar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun populateAdapter() {

        Section(HeaderItem(R.string.top_ten_card_title)).apply {
            add(makeStandingsCarouselItem())
            groupAdapter.add(this)
        }
    }

    private fun makeStandingsCarouselItem(): StandingsCarouselItem {
        val paint = Paint()
        paint.color = background
        val carouselDecoration = StandingsItemDecoration(resources.getDimensionPixelSize(R.dimen.carousel_horizontal_padding), paint)
        val carouselAdapter = GroupAdapter<ViewHolder>()
        mainActivityViewModel.queryTopTenTeams()
        mainActivityViewModel.topTenTeams.forEach {

        }
    }
}