package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener

import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.appbar_main.*
import re.spitfy.ctftime.R
import re.spitfy.ctftime.adapters.RankingsFirestoreAdapter
import re.spitfy.ctftime.data.Ranking

class TeamRankingsFragment :
        android.support.v4.app.Fragment(),
        AdapterView.OnItemSelectedListener
{
    private lateinit var year: String
    private lateinit var rankingsYear : String
    private var initiated = false
    private var pageLoaded = false
    private lateinit var adapter : RankingsFirestoreAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var progressBarLoadingRankings : ProgressBar
    private lateinit var progressBarLoadingRankingsBig : ProgressBar
    private lateinit var recyclerView : RecyclerView
    private var rankingsList : MutableList<Ranking> = ArrayList()
    private var pageCount : Int = 0

    companion object
    {
        val TAG = "TeamRankingsFragment"
        val PAGE_LENGTH : Long = 50
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
        val yearArg = arguments?.getString("YEAR")
        if (yearArg != null) {
            year = yearArg
            rankingsYear = "${year}_Rankings"
        } else {
            Log.d(TAG, "No arguments. Did you create " +
                    "TeamRankingsFragment instance with newInstance method?")
        }
        //TODO: Check Internet connectivity

        // Initialize database connection
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val rootView = inflater.inflate(
                R.layout.fragment_rankings,
                container,
                false)
        rootView.tag = TAG + year
        activity.toolbar.title = "Team Rankings"
        // Rankings RecyclerView instantiation
        recyclerView = rootView.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)

        // Ranking spinner instantiation
        val yearSpinner = rootView.findViewById<Spinner>(R.id.rankings_spinner)
        spinnerInitiate(yearSpinner)

        progressBarLoadingRankings = rootView.findViewById(R.id.progressBarRankings)
        progressBarLoadingRankingsBig = rootView.findViewById(R.id.progressBarRankingsBig)
        progressBarLoadingRankingsBig.visibility = View.VISIBLE

        adapter = RankingsFirestoreAdapter(rankingsList)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        // Rankings layout instantiation
        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rankingLayoutManager.isAutoMeasureEnabled = false
        recyclerView.layoutManager = rankingLayoutManager

        //TODO: Use InfiniteScrollListener to make pagination logic easier
        recyclerView.addOnScrollListener(object : InfiniteScrollListener(PAGE_LENGTH.toInt(), rankingLayoutManager) {
            override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                progressBarLoadingRankings.visibility = View.VISIBLE
                if (pageLoaded) {
                    getRankings(pageCount * PAGE_LENGTH.toInt())
                    refreshView(recyclerView, RankingsFirestoreAdapter(rankingsList), firstVisibleItemPosition)
                    pageLoaded = false
                }
            }
        })

        getRankings(pageCount)

        return rootView ?: throw IllegalStateException(
                "LayoutInflater is null in onCreateView. "
                + "Unable to inflate view.")
    }

    fun getRankings(startRank : Int) {
        val query : Query?
        if (startRank == 0) {
            query =  db.collection(rankingsYear)
                    .orderBy("Rank", Query.Direction.ASCENDING)
                    .limit(PAGE_LENGTH)
        } else {
            query = db.collection(rankingsYear)
                    .orderBy("Rank", Query.Direction.ASCENDING)
                    .startAt(startRank+1)
                    .limit(PAGE_LENGTH)
        }
        query.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                val newRankings : MutableList<Ranking> = ArrayList()
                if (querySnapshot != null) {
                    querySnapshot.documents.forEach {
                        newRankings.add(it.toObject(Ranking::class.java))
                    }
                    rankingsList.addAll(newRankings)
                    if (pageCount == 0) {
                        progressBarLoadingRankingsBig.visibility = View.GONE
                        Log.d(TAG, "Finished loading initial data set")
                    } else {
                        progressBarLoadingRankings.visibility = View.GONE
                        Log.d(TAG, "Finished loading additional data set at " + startRank.toString())
                    }
                    pageCount++
                    pageLoaded = true
                }
            }
        })
    }
    private fun spinnerInitiate(spinner: Spinner) {
        val rankingsArray = activity?.resources?.getStringArray(R.array.ranking_years)
        val yearSpinnerAdapter = ArrayAdapter<String>(activity, R.layout.spinner_head, rankingsArray)
        yearSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = yearSpinnerAdapter
        val yearPosition = yearSpinnerAdapter.getPosition(year)
        spinner.setSelection(yearPosition, false)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (initiated) {
            val newYear = p0?.getItemAtPosition(p2).toString()
            activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container, TeamRankingsFragment.newInstance(newYear), newYear)
                    ?.commit()
        } else {
            initiated = true
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }
}