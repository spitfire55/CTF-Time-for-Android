package re.spitfy.ctftime.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.R

class TeamRankViewHolder(itemView: View, private val parent: ViewGroup?): RecyclerView.ViewHolder(itemView)
{
    private val rankView = itemView.findViewById<TextView>(R.id.team_rank_textview)
    private val teamNameView = itemView.findViewById<TextView>(R.id.team_name_textview)
    private val teamCountryFlagView = itemView.findViewById<ImageView>(R.id.team_country_imageview)
    private val pointsView = itemView.findViewById<TextView>(R.id.team_points_textview)

    fun bind(rankData: Team?, year: String, recyclerIndex : Int) {
        if (rankData != null) {
            rankView.text = recyclerIndex.toString()
            teamNameView.text = rankData.Name
            Picasso.with(parent?.context)
                    .load("https://ctftime.org/static/images/f/" +
                            "${rankData.CountryCode.toLowerCase()}.png"
                    )
                    .into(teamCountryFlagView)
            pointsView.text = rankData.Scores[year]?.Points?.format(3)?.toString()
        }
    }

    private fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
}