package re.spitfy.ctftime.Fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import re.spitfy.ctftime.Adapters.TeamRankingsAdapter
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R
import re.spitfy.ctftime.SimpleDividerItemDecoration

class TeamRankingsFragment: android.support.v4.app.Fragment()
{
    companion object {
        val TAG = "TeamRankingsFragment"
        lateinit var data: Array<TeamRankData>
        val rankingRef: Query? =
                FirebaseDatabase
                        .getInstance()
                        .getReference("/Rankings/2017")
                        .orderByChild("points")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater?.
                inflate(R.layout.fragment_team_rankings,
                        container,
                        false)
        rootView?.tag = TAG

        val recyclerView = rootView?.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        val adapter = TeamRankingsAdapter(rankingRef, this.context)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(SimpleDividerItemDecoration(this.context))

        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rankingLayoutManager.reverseLayout = true
        rankingLayoutManager.stackFromEnd = true
        recyclerView?.layoutManager = rankingLayoutManager


        return rootView ?: throw IllegalStateException(
                "LayoutInflater is null in onCreateView. "
                + "Unable to inflate view.")
    }
}