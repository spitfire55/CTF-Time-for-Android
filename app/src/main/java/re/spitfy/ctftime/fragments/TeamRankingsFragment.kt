package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import re.spitfy.ctftime.adapters.TeamRankingsAdapter
import re.spitfy.ctftime.R

class TeamRankingsFragment : android.support.v4.app.Fragment()
{
    private lateinit var year: String

    companion object
    {
        val TAG = "TeamRankingsFragment"

        fun newInstance(year: String): TeamRankingsFragment
        {
            val args = Bundle()
            args.putString("YEAR", year)
            val fragment = TeamRankingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val yearArg = arguments.getString("YEAR")
        if (yearArg != null) {
            year = yearArg
        } else {
            Log.d(TAG, "No arguments. Did you create TeamRankingsFragment " +
                    "instance with newInstance method")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {

        val rootView = inflater?.inflate(
                R.layout.fragment_rankings,
                container,
                false)
        rootView?.tag = TAG + year

        val recyclerView = rootView?.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        if (recyclerView == null) {
            Log.d(TAG, "Recyclerview not found?")
        } else {
            startRecyclerView(recyclerView, year)
        }

        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rankingLayoutManager.reverseLayout = true
        rankingLayoutManager.stackFromEnd = true
        recyclerView?.layoutManager = rankingLayoutManager

        return rootView ?: throw IllegalStateException(
                "LayoutInflater is null in onCreateView. "
                + "Unable to inflate view.")
    }

    private fun startRecyclerView(recyclerView: RecyclerView,
                                  rankingsYear: String)
    {
        val rankingRef = FirebaseDatabase.getInstance()
                .getReference("Rankings/" + rankingsYear)
                .orderByChild("points")

        val recyclerViewAdapter = TeamRankingsAdapter(rankingRef, this.context)
        recyclerView.adapter = recyclerViewAdapter
    }
}