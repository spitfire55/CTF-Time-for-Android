package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.database.*
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import re.spitfy.ctftime.adapters.TeamRankingsAdapter
import re.spitfy.ctftime.R

class TeamRankingsFragment : android.support.v4.app.Fragment()
{
    private lateinit var year: String
    private var pageNumber = 0

    companion object
    {
        val TAG = "TeamRankingsFragment"

        fun newInstance(year: String, pageNumber: Int): TeamRankingsFragment
        {
            val args = Bundle()
            args.putString("YEAR", year)
            args.putInt("PAGE", pageNumber)
            val fragment = TeamRankingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val yearArg = arguments.getString("YEAR")
        val pageArg = arguments.getInt("PAGE")
        if (yearArg != null && pageArg != 0) {
            year = yearArg
            pageNumber = pageArg
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

        val prevPageButton = rootView?.findViewById<Button>(R.id.leftButton)
        prevPageButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                activity.supportFragmentManager.popBackStackImmediate()
            }
        })
        if (pageNumber == 1) {
            prevPageButton?.isClickable = false
        }
        val nextPageButton = rootView?.findViewById<Button>(R.id.rightButton)
        nextPageButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFrame,
                                TeamRankingsFragment.newInstance(year, pageNumber+1),
                                "$year-$pageNumber")
                        .addToBackStack(null)
                        .commit()
            }
        })

        val recyclerView = rootView?.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        if (recyclerView == null) {
            Log.d(TAG, "Recyclerview not found?")
        } else {
            recyclerView.setHasFixedSize(true)
            startRecyclerView(recyclerView, year)
        }

        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = rankingLayoutManager


        return rootView ?: throw IllegalStateException(
                "LayoutInflater is null in onCreateView. "
                + "Unable to inflate view.")
    }

    private fun startRecyclerView(recyclerView: RecyclerView,
                                  rankingsYear: String)
    {
        val rankingRef = FirebaseDatabase
                .getInstance()
                .getReference("Teams")
                .orderByChild("rating/$year/rating_place")
                .limitToFirst(50).startAt((pageNumber - 1) * 50 + 1.0)

        val recyclerViewAdapter = TeamRankingsAdapter(year, rankingRef, this.context)
        recyclerView.adapter = recyclerViewAdapter
    }
}