package re.spitfy.ctftime.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R

class TeamRankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    private val rankView = itemView
            .findViewById<TextView>(R.id.team_rank_textview)
    private val teamNameView = itemView
            .findViewById<TextView>(R.id.team_name_textview)
    private val pointsView = itemView
            .findViewById<TextView>(R.id.team_points_textview)

    fun bind(rankData: TeamRankData, rankValue: String) {
        rankView.text = rankValue
        teamNameView.text = rankData.team_name
        pointsView.text = rankData.points.toString()
    }
}