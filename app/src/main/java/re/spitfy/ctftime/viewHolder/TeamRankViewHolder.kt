package re.spitfy.ctftime.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import re.spitfy.ctftime.data.TeamRankData
import re.spitfy.ctftime.R

class TeamRankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    private val rankView = itemView
            .findViewById<TextView>(R.id.team_rank_textview)
    private val teamNameView = itemView
            .findViewById<TextView>(R.id.team_name_textview)
    private val pointsView = itemView
            .findViewById<TextView>(R.id.team_points_textview)

    fun bind(rankData: TeamRankData?, year: String) {
        if (rankData != null) {
            rankView.text = rankData.Ratings[year]?.RatingPlace.toString()
            teamNameView.text = rankData.Name
            pointsView.text = rankData.Ratings[year]?.RatingPoints?.format(3)
        }
    }

    fun Float.format(digits: Int) = java.lang.String
            .format("%.${digits}f", this)
}