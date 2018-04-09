package re.spitfy.ctftime.view.item

import android.support.annotation.StringRes
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_header.*
import re.spitfy.ctftime.R

class HeaderItem(@StringRes private val title: Int): Item() {

    override fun getLayout(): Int = R.layout.item_header

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_home_headerTitle.setText(title)
    }
}