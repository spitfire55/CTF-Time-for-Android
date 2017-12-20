package re.spitfy.ctftime.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.Ranking
import re.spitfy.ctftime.viewHolder.RankingProgressBarViewHolder
import re.spitfy.ctftime.viewHolder.TeamRankViewHolder

class RankingsFirestoreAdapter : RecyclerView.Adapter<TeamRankViewHolder>() {

    companion object {
        private val ITEM_VIEW = 0
        private val LOADING_VIEW = 1
    }
    private val rankingsList : MutableList<Ranking> = ArrayList()

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

    fun appendRankings(newRankings : List<Ranking>) {
        val currentSize = rankingsList.size
        val newSize = newRankings.size
        rankingsList.addAll(newRankings)
        notifyItemRangeInserted(currentSize, newSize)
    }

    fun getLastRank() : Int {
        return rankingsList[rankingsList.size - 1].Rank
    }
}