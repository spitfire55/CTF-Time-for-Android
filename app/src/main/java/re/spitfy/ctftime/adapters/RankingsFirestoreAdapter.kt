package re.spitfy.ctftime.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.Ranking
import re.spitfy.ctftime.viewHolder.TeamRankViewHolder

class RankingsFirestoreAdapter(val rankingsList : MutableList<Ranking>) : RecyclerView.Adapter<TeamRankViewHolder>() {


    override fun onBindViewHolder(holder: TeamRankViewHolder?, position: Int) {
        holder?.bind(rankingsList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TeamRankViewHolder? {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.team_rankings_row,
                        parent,
                        false)
        return TeamRankViewHolder(v, parent)
    }

    override fun getItemCount(): Int {
        return rankingsList.size
    }
}