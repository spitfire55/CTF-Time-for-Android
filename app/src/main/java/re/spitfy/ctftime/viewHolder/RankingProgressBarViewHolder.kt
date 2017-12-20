package re.spitfy.ctftime.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import re.spitfy.ctftime.R

class RankingProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)
}