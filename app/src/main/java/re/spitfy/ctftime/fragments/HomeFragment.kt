package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import re.spitfy.ctftime.R
import re.spitfy.ctftime.adapters.TopTenTeamsAdapter
import re.spitfy.ctftime.data.Team

class HomeFragment : android.support.v4.app.Fragment() {
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var topTenTeams : MutableList<Team> = ArrayList()
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        rootView.tag = TAG
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_home_topTen)
        swipeRefreshLayout.setOnRefreshListener {
            refreshTopTenCard(view)
        }
        populateTopTenCard(view, false)
    }

    private fun populateTopTenCard(rootView: View, isRefresh: Boolean) {
        val topTenView = rootView.findViewById<LinearLayout>(R.id.linearLayout_home_topTen)
        val query = db.collection("Teams")
                .orderBy("Scores.2018.Points", Query.Direction.DESCENDING)
                .whereGreaterThan("Scores.2018.Points", 0)
                .limit(10)
            query.addSnapshotListener {
                querySnapshot,
                _
            ->
            if (querySnapshot != null && !querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    topTenTeams.add(document.toObject(Team::class.java))
                }
                val topTenAdapter = TopTenTeamsAdapter(context, topTenTeams)
                (0 until 10).forEach {
                    val topTeamRow = topTenAdapter.getView(it, null, null) as LinearLayout
                    val params = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (resources.displayMetrics.density * 48).toInt()
                    )
                    topTeamRow.layoutParams = params
                    topTenView.addView(topTeamRow)
                }
            }
            if (isRefresh) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun refreshTopTenCard(rootView: View) {
        val topTenView = rootView.findViewById<LinearLayout>(R.id.linearLayout_home_topTen)
        topTenView.removeAllViews()
        populateTopTenCard(rootView, true)
    }


}