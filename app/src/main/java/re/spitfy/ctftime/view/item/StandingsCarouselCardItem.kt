package re.spitfy.ctftime.view.item

import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import re.spitfy.ctftime.R

class StandingsCarouselCardItem(val year: String): Item() {
    override fun getLayout(): Int = R.layout.item_standings_carousel_card

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text =
    }
}