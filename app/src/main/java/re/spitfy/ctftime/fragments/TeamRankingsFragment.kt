package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import re.spitfy.ctftime.adapters.TeamRankingsAdapter
import re.spitfy.ctftime.R

class TeamRankingsFragment : android.support.v4.app.Fragment(), AdapterView.OnItemSelectedListener
{
    private lateinit var year: String
    private var pageNumber = -1

    companion object
    {
        val TAG = "TeamRankingsFragment"

        fun newInstance(year: String, pageNumber: Int): TeamRankingsFragment
        {
            val args = Bundle()
            args.putString("YEAR", year)
            args.putInt("PAGE", pageNumber)
            Log.d(TAG, year + "-" + pageNumber)
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
        Log.d(TAG, pageArg.toString())
        if (yearArg != null && pageArg != -1) {
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

        // Previous button instantiation
        val prevPageButton = rootView?.findViewById<Button>(R.id.leftButton)
        prevPageButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                activity.supportFragmentManager.popBackStackImmediate()
            }
        })
        prevPageButton?.isClickable = (pageNumber != 0)
        Log.d(TAG, prevPageButton?.isClickable.toString())
        //Next button instantiation
        val nextPageButton = rootView?.findViewById<Button>(R.id.rightButton)
        nextPageButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                val nextPage = pageNumber + 1
                activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFrame,
                                TeamRankingsFragment.newInstance(year, nextPage),
                                "$year-$pageNumber")
                        .addToBackStack(null)
                        .commit()
            }
        })
        // Rankings RecyclerView instantiation
        val recyclerView = rootView?.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)
        if (recyclerView == null) {
            Log.d(TAG, "Recyclerview not found?")
        } else {
            recyclerView.setHasFixedSize(true)
            startRecyclerView(recyclerView, year)
        }
        // Rankings layout instantiation
        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = rankingLayoutManager

        // Ranking spinner instantiation
        val yearSpinner = rootView?.findViewById<Spinner>(R.id.rankings_spinner)
        val rankingsArray = activity.resources.getStringArray(R.array.ranking_years)
        val yearSpinnerAdapter = ArrayAdapter<String>(activity, R.layout.spinner_item, rankingsArray)
        yearSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        yearSpinner?.adapter = yearSpinnerAdapter
        val yearPosition = yearSpinnerAdapter.getPosition(year)
        yearSpinner?.setSelection(yearPosition, false)
        yearSpinner?.onItemSelectedListener = this

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
                .limitToFirst(50).startAt((pageNumber) * 50 + 1.0)

        val recyclerViewAdapter = TeamRankingsAdapter(year, rankingRef, this.context)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val newYear = p0?.getItemAtPosition(p2).toString()
        Log.d(TAG, newYear)
        val stackFrag = activity.supportFragmentManager.findFragmentByTag("$newYear-0")
        if (stackFrag != null) {
            Log.d(TAG, "Not null")
            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainFrame,
                            stackFrag,
                            "$newYear-0")
                    .addToBackStack(null)
                    .commit()
        } else {
            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainFrame,
                            TeamRankingsFragment.newInstance(newYear, 0),
                            "$newYear-0")
                    .addToBackStack(null)
                    .commit()
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Do nothing
    }
}