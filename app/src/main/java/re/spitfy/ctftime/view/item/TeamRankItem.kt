package re.spitfy.ctftime.view.item

import android.support.v7.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_standings_carousel.*
import re.spitfy.ctftime.R
import re.spitfy.ctftime.view.decoration.StandingsItemDecoration

/*
 * This class implements the logic behind generating a horizontal RecyclerView to show the standings
 * for each year that CTF Time has been around (2011-2018 as of now).
 *
 * Parameters:
 * - carouselDecoration : A RecyclerView.ItemDecoration that will handle things like snapping to
 * start of view, custom background color, and some material design goodness
 * - standingsCarouselAdapter : A groupie GroupAdapter that will handle the underlying children of
 * the horizontal RecyclerView. As a result, the bind should just be to assign this adapter to the
 * horizontal RecyclerView.
 */
class StandingsCarouselItem(
        private val carouselDecoration: StandingsItemDecoration,
        private val standingsCarouselAdapter: GroupAdapter<ViewHolder>
): Item() {

    override fun getLayout() = R.layout.item_standings_carousel

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.recyclerview_standings.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = standingsCarouselAdapter
            //Prevent item decoration from being registered multiple times if already bound by
            //removing then adding
            removeItemDecoration(carouselDecoration)
            addItemDecoration(carouselDecoration)
        }
    }
}