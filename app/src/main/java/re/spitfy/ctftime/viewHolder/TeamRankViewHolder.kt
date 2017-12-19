package re.spitfy.ctftime.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import re.spitfy.ctftime.data.Ranking
import re.spitfy.ctftime.R

class TeamRankViewHolder(itemView: View, private val parent: ViewGroup?): RecyclerView.ViewHolder(itemView)
{
    private val rankView = itemView
            .findViewById<TextView>(R.id.team_rank_textview)
    private val teamNameView = itemView
            .findViewById<TextView>(R.id.team_name_textview)
    private val teamCountryFlagView = itemView
            .findViewById<ImageView>(R.id.team_country_imageview)
    private val pointsView = itemView
            .findViewById<TextView>(R.id.team_points_textview)

    fun bind(rankData: Ranking?) {
        if (rankData != null) {
            rankView.text = rankData.Rank.toString()
            teamNameView.text = rankData.TeamName
            Picasso.with(parent?.context)
                    .load("https://ctftime.org%s".format(rankData.CountryFlag))
                    .into(teamCountryFlagView)
            pointsView.text = rankData.Score.format(3).toString()
        }
    }

    private fun Float.format(digits: Int) = java.lang.String
            .format("%.${digits}f", this)
}