package re.spitfy.ctftime.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.viewholder.TeamRankViewHolder

class RankingsFirestoreAdapter(
        private val rankingsList : MutableList<Team>,
        private val year: String
) : RecyclerView.Adapter<TeamRankViewHolder>() {

    override fun onBindViewHolder(holder: TeamRankViewHolder, position: Int) {
        holder.bind(rankingsList[position], year, position + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamRankViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.team_rankings_row, parent, false)
        return TeamRankViewHolder(v, parent)
    }

    override fun getItemCount(): Int {
        return rankingsList.size
    }
}