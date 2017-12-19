package re.spitfy.ctftime.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import pl.droidsonroids.gif.GifImageView
import re.spitfy.ctftime.R

class RankingProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val progressBar : GifImageView = itemView.findViewById(R.id.progressBar)
}