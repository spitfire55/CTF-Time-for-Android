package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.jetbrains.anko.coroutines.experimental.bg
import re.spitfy.ctftime.R
import re.spitfy.ctftime.adapters.RankingsAdapter
import re.spitfy.ctftime.data.TeamRankData

class TeamRankingsFragment :
        android.support.v4.app.Fragment(),
        AdapterView.OnItemSelectedListener
{
    private lateinit var year: String
    private var pageNumber = -1
    private var userClick = false
    private lateinit var adapter: RankingsAdapter
    private val logTag = "TeamRankingsFragment"
    companion object
    {
        val TAG = "TeamRankingsFragment"
        val navTracker = IntArray(7) // keeps track of page number state across years

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
        val yearArg = arguments?.getString("YEAR")
        val pageArg = arguments?.getInt("PAGE")
        if (yearArg != null && pageArg != null) {
            year = yearArg
            pageNumber = pageArg
            val index = year.toInt() - 2011
            navTracker[index] = pageNumber
        } else {
            Log.d(TAG, "No arguments. Did you create " +
                    "TeamRankingsFragment instance with newInstance method?")
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_rankings,
                container,
                false)
        rootView?.tag = TAG + year

        // Previous button instantiation
        val prevPageButton = rootView?.findViewById<Button>(R.id.leftButton)
        prevPageButton?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val prevPage = pageNumber - 1
                activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.mainFrame,
                                TeamRankingsFragment.newInstance(year, prevPage),
                                year)
                        ?.commit()
            }
        })
        prevPageButton?.isClickable = (pageNumber != 0)
        //Next button instantiation
        val nextPageButton = rootView?.findViewById<Button>(R.id.rightButton)
        nextPageButton?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val nextPage = pageNumber + 1
                activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.mainFrame,
                                TeamRankingsFragment.newInstance(year, nextPage),
                                year)
                        ?.commit()
            }
        })
        // Rankings RecyclerView instantiation
        val recyclerView = rootView?.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        if (recyclerView == null) {
            Log.d(TAG, "Recyclerview not found.")
        } else {
            recyclerView.setHasFixedSize(true)
            Log.d(logTag, "Got here...")
            startRecyclerView(recyclerView)
            if (adapter.itemCount != 50) {
                nextPageButton?.isClickable = false
            }
        }
        // Rankings layout instantiation
        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = rankingLayoutManager

        // Ranking spinner instantiation
        val yearSpinner = rootView?.findViewById<Spinner>(R.id.rankings_spinner)
        val rankingsArray = activity?.resources?.getStringArray(R.array.ranking_years)
        val yearSpinnerAdapter = ArrayAdapter<String> (
                activity,
                R.layout.spinner_head,
                rankingsArray )
        yearSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        yearSpinner?.adapter = yearSpinnerAdapter
        val yearPosition = yearSpinnerAdapter.getPosition(year)
        yearSpinner?.setSelection(yearPosition, false)
        yearSpinner?.onItemSelectedListener = this

        return rootView ?: throw IllegalStateException(
                "LayoutInflater is null in onCreateView. "
                        + "Unable to inflate view.")
    }
    private fun startRecyclerView(recyclerView: RecyclerView) {
            val rankings = ArrayList<TeamRankData>()
            adapter = RankingsAdapter(rankings)
            bg {
                Log.d(logTag, "In background thread")
                adapter.parse(year, pageNumber+1)
            }
            recyclerView.adapter = adapter
    }

    override fun onItemSelected(p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long)
    {
        if (userClick) {
            val newYear = p0?.getItemAtPosition(p2).toString()
            val index = newYear.toInt() - 2011
            activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.mainFrame,
                             TeamRankingsFragment.newInstance(newYear,
                                    navTracker[index]),
                                newYear)
                    ?.commit()
        }
        else {
            userClick = true
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }
}