package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.google.firebase.firestore.*
import re.spitfy.ctftime.R
import re.spitfy.ctftime.adapters.OnLoadMoreListener
import re.spitfy.ctftime.adapters.RankingsFirestoreAdapter
import re.spitfy.ctftime.data.Ranking

class TeamRankingsFragment :
        android.support.v4.app.Fragment(),
        AdapterView.OnItemSelectedListener
{
    private lateinit var year: String
    private lateinit var rankingsYear : String
    private var userClick = false
    private lateinit var adapter : RankingsFirestoreAdapter
    private var rankingList : MutableList<Ranking?> = ArrayList()
    private lateinit var db : FirebaseFirestore
    private lateinit var lastVisible : DocumentSnapshot
    private lateinit var prevItemVisible : DocumentSnapshot

    companion object
    {
        val TAG = "TeamRankingsFragment"
        val pageLength : Long = 20

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
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val rootView = inflater.inflate(
                R.layout.fragment_rankings,
                container,
                false)
        rootView?.tag = TAG + year

        // Rankings RecyclerView instantiation
        val recyclerView = rootView?.
                findViewById<RecyclerView>(R.id.team_ranking_recyclerview)

        // Ranking spinner instantiation
        val yearSpinner = rootView.findViewById<Spinner>(R.id.rankings_spinner)
        spinnerInitiate(yearSpinner)

        // Rankings layout instantiation
        val rankingLayoutManager = LinearLayoutManager(activity)
        rankingLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = rankingLayoutManager

        adapter = RankingsFirestoreAdapter(rankingList, recyclerView)
        recyclerView?.adapter = adapter

        // Rankings data instantiation
        db = FirebaseFirestore.getInstance()
        loadData()

        adapter.onLoadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                recyclerView?.post(object: Runnable {
                    override fun run() {
                        rankingList.add(null)
                        adapter.notifyItemInserted(rankingList.size - 1)
                    }
                })
                Handler().post(object : Runnable {
                    override fun run() {
                        loadMoreData()
                    }
                })
            }
        }
        return rootView ?: throw IllegalStateException(
                "LayoutInflater is null in onCreateView. "
                + "Unable to inflate view.")
    }

    private fun loadData() {
        db.collection(rankingsYear).orderBy("Rank", Query.Direction.ASCENDING)
                .limit(pageLength).addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (querySnapshot != null) {
                    for (snapshot in querySnapshot.documents) {
                        val model = snapshot.toObject(Ranking::class.java)
                        rankingList.add(model)
                        adapter.notifyDataSetChanged()
                        lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
                    }
                }
            }
        })
    }

    private fun loadMoreData(){
        db.collection(rankingsYear).orderBy("Rank", Query.Direction.ASCENDING)
                .startAt(lastVisible)
                .limit(pageLength).addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (querySnapshot != null) {
                    for (snapshot in querySnapshot.documents) {
                        val model = snapshot.toObject(Ranking::class.java)
                        rankingList.add(model)
                        adapter.notifyDataSetChanged()
                        lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
                        adapter.isLoading = false
                    }

                    db.collection(rankingsYear)
                            .orderBy("Rank", Query.Direction.ASCENDING)
                            .addSnapshotListener(object: EventListener<QuerySnapshot> {
                                override fun onEvent(querySnapshot2: QuerySnapshot?,
                                                     e: FirebaseFirestoreException?) {
                                    if (querySnapshot2 != null) {
                                        prevItemVisible = querySnapshot2
                                                .documents[querySnapshot2.size() - 1]
                                        if (prevItemVisible.id == lastVisible.id) {
                                            adapter.isAllLoaded = true
                                        }
                                    }
                                }
                            })
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
        if (userClick) {
            val newYear = p0?.getItemAtPosition(p2).toString()
            activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container, TeamRankingsFragment.newInstance(newYear), newYear)
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