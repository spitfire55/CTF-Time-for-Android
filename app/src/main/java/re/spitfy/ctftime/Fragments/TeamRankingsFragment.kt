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

class TeamRankingsFragment: android.support.v4.app.Fragment() {
    companion object {
        val TAG = "TeamRankingsFragment"
        lateinit var data: Array<TeamRankData>
        val rankingRef: Query? = FirebaseDatabase.getInstance().getReference("/2017").orderByChild("points")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater?.inflate(
                R.layout.fragment_team_rankings, container, false)
        rootView?.tag = TAG

        val recyclerView = rootView?.findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        val adapter = TeamRankingsAdapter(rankingRef)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        return rootView ?: throw IllegalStateException("LayoutInflater is null in onCreateView. Unable to inflate view.")
    }

    override fun onStart() {
        super.onStart()

        rankingRef?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot?) {
                var rankings: MutableList<TeamRankData> = arrayListOf()
                snapshot?.children?.forEach {
                    val rank = it.getValue(TeamRankData::class.java)
                    if (rank != null) {
                        rankings.add(rank)
                    }
                }
                data = rankings.toTypedArray()
            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, p0?.code.toString())
            }
        })
    }
}