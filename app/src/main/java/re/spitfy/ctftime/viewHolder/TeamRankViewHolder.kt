package re.spitfy.ctftime.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import re.spitfy.ctftime.data.TeamRankData
import re.spitfy.ctftime.R

class TeamRankViewHolder(itemView: View, private val parent: ViewGroup?): RecyclerView.ViewHolder(itemView)
{
    private val rankView = itemView
            .findViewById<TextView>(R.id.team_rank_textview)
    private val teamNameView = itemView
            .findViewById<TextView>(R.id.team_name_textview)
    private val countryFlagView = itemView
            .findViewById<ImageView>(R.id.team_country_imageview)
    private val pointsView = itemView
            .findViewById<TextView>(R.id.team_points_textview)

    fun bind(rankData: TeamRankData?) {
        if (rankData != null) {
            rankView.text = rankData.Rank.toString()
            teamNameView.text = rankData.Name
            Picasso.with(parent?.context)
                    .load("http://ctftime.org%s".format(rankData.CountryFlagUrl))
                    .into(countryFlagView)
            pointsView.text = rankData.Points.format(2).toString()
        }
    }

    fun Float.format(digits: Int) = java.lang.String
            .format("%.${digits}f", this)
}