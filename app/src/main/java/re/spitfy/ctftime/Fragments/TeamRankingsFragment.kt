package re.spitfy.ctftime.Fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import re.spitfy.ctftime.Adapters.TeamRankingsAdapter
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R

class TeamRankingsFragment: android.support.v4.app.Fragment() {
    companion object {
        val TAG = "TeamRankingsFragment"
        lateinit var data: Array<TeamRankData>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acquireRankings()
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater?.inflate(
                R.layout.fragment_team_rankings, container, false)
        rootView?.tag = TAG

        val recyclerView = rootView?.findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        val adapter = TeamRankingsAdapter(data)
        recyclerView?.adapter = adapter

        return rootView ?: throw IllegalStateException("LayoutInflater is null in onCreateView. Unable to inflate view.")
    }

    private fun acquireRankings() {

    }
}