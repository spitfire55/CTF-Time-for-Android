package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import re.spitfy.ctftime.R
import re.spitfy.ctftime.adapters.TeamAliasesAdapter
import re.spitfy.ctftime.adapters.TeamMembersAdapter
import re.spitfy.ctftime.adapters.TeamPastResultsAdapter
import re.spitfy.ctftime.data.Score
import re.spitfy.ctftime.data.ScoreAndYear
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.utils.format
import re.spitfy.ctftime.utils.hideKeyboardFrom
import java.io.Serializable

class OldTeamProfileFragment : android.support.v4.app.Fragment() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var team: Team
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var autoCompleteProgressBar: ProgressBar
    private var teamNameArray: MutableList<String> = ArrayList()
    private var teamArray: MutableList<Team> = ArrayList()
    private var query: Query? = null
    private var queryRegistration: ListenerRegistration? = null

    companion object {
        const val TAG = "TeamProfileFragment"
        const val SUGGESTION_LIMIT: Long = 3
        fun newInstance(team: Team?): TeamProfileFragment {
            val args = Bundle()
            args.putSerializable("Team", team as? Serializable)
            val fragment = TeamProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_team_profile, container, false)
        team = if (savedInstanceState != null) {
            savedInstanceState.getSerializable("Team") as Team
        } else {
            arguments?.getSerializable("Team") as? Team
                    ?: Team()
        }
        rootView.tag = TAG + team.Name
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("Team", team as? Serializable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        queryRegistration?.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //activity?.toolbar?.title = "Team Profile"
        autoCompleteTextView = view.findViewById(R.id.appCompatAutoCompleteTextView_team_searchTeam)
        autoCompleteProgressBar = view.findViewById(R.id.progressBar_team_searchTeamStatus)
        autoCompleteTextView.setOnFocusChangeListener({ _, hasFocus ->
            if (hasFocus) {
                autoCompleteTextView.hint = ""
                autoCompleteTextView.isCursorVisible = true
            }
        })
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.hint = ""
            autoCompleteTextView.isCursorVisible = true
        }
        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, pos, _ ->
            hideKeyboardFrom(context, view)
            val newTeamName = adapterView?.getItemAtPosition(pos).toString()
            if (newTeamName != team.Name) {
                if (team.Name == "") {
                    // don't preserve blank Team fragment
                    activity?.supportFragmentManager?.popBackStack()
                }
                autoCompleteTextView.setText("")
                /*activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(
                                R.id.container,
                                TeamProfileFragment.newInstance(teamArray[pos]),
                                newTeamName
                        )?.addToBackStack(null)
                        ?.commit()
                        */
            }
        }
        autoCompleteTextView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val newTeamName = parent?.getItemAtPosition(position).toString()
                if (newTeamName != team.Name) {
                    autoCompleteTextView.setText("")
                    /*activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(
                                    R.id.container,
                                    TeamProfileFragment.newInstance(teamArray[position]),
                                    newTeamName
                            )?.addToBackStack(null)
                            ?.commit()
                            */
                }
            }
        }
        setAutoCompleteListener()
        if (team.Name != "") {
            populateGeneralCard(view)
            populatePastResultsCard(view)
            populateMembersCard(view)
            populateAliasesCard(view)
        } else {
            // hide blank views
            view.findViewById<CardView>(R.id.card_team_generalInfo).visibility = View.GONE
            view.findViewById<CardView>(R.id.card_team_pastResults).visibility = View.GONE
            view.findViewById<CardView>(R.id.card_team_members).visibility = View.GONE
            view.findViewById<CardView>(R.id.card_team_aliases).visibility = View.GONE
            view.findViewById<ConstraintLayout>(R.id.frame_team_blank).visibility = View.VISIBLE
        }
    }

    private fun setAutoCompleteListener() {
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSequence != null && charSequence.isNotBlank()) {
                    autoCompleteTextView.dismissDropDown()
                    autoCompleteProgressBar.visibility = View.VISIBLE
                    queryRegistration?.remove() // remove previous request
                    retrieveTeamNameSuggestions(charSequence.toString().trim().toLowerCase())
                } else {
                    queryRegistration?.remove()
                    autoCompleteTextView.dismissDropDown()
                    autoCompleteProgressBar.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(input: Editable?) {}
        })
    }

    private fun retrieveTeamNameSuggestions(input: String) {
        query = db.collection("Teams")
                .orderBy("NameCaseInsensitive", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("NameCaseInsensitive", input)
                .whereLessThanOrEqualTo("NameCaseInsensitive", input + "\uf8ff")
                .limit(SUGGESTION_LIMIT)
        queryRegistration = query?.addSnapshotListener { querySnapshot,
                                                         _
            ->
            if (querySnapshot != null && !querySnapshot.isEmpty) {
                teamArray.clear()
                teamNameArray.clear()
                for (document in querySnapshot.documents) {
                    teamArray.add(document.toObject(Team::class.java))
                    teamNameArray.add(document.getString("Name"))
                }
                if (activity != null) {
                    val arrayAdapter = ArrayAdapter<String>(
                            activity,
                            android.R.layout.select_dialog_item,
                            teamNameArray
                    )
                    autoCompleteTextView.setAdapter(arrayAdapter)
                    autoCompleteProgressBar.visibility = View.INVISIBLE
                    autoCompleteTextView.showDropDown()
                } else {
                    Log.d(TAG, "Null activity")
                }
            } else {
                autoCompleteProgressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun populateGeneralCard(rootView: View) {

        val currentRankingsContainerView =
                rootView.findViewById<ConstraintLayout>(R.id.constraintLayout_team_currentRanking)
        val currentRankingsDividerView =
                rootView.findViewById<View>(R.id.view_team_nameCurrentScoreSeperator)
        val rankValueView =
                rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_rankValue)
        val pointsValueView =
                rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_pointsValue)
        val nameView = rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_name)

        nameView.text = team.Name
        if (team.Scores["2018"] == null || team.Scores["2018"]?.Rank == 0) {
            currentRankingsContainerView.visibility = View.GONE
            currentRankingsDividerView.visibility = View.GONE
        } else {
            rankValueView.text = team.Scores["2018"]?.Rank?.toString()
            pointsValueView.text = team.Scores["2018"]?.Points?.format(2)

        }
        if (team.Logo != "/static/images/nologo.png") {
            Picasso.with(context)
                    .load("https://ctftime.org/${team.Logo}")
                    .into(rootView.findViewById<ImageView>(R.id.image_team_logo))
        } else {
            rootView.findViewById<ConstraintLayout>(R.id.constraintLayout_team_logo).visibility =
                    View.GONE
        }
    }

    private fun populateAliasesCard(rootView: View) {
        val aliasView = rootView.findViewById<GridLayout>(R.id.grid_team_aliases)
        val aliases = team.Aliases
        if (aliases == null) {
            rootView.findViewById<CardView>(R.id.card_team_aliases).visibility = View.GONE
        } else {
            val aliasAdapter = TeamAliasesAdapter(context, aliases)
            (0 until aliases.size).forEach {
                val item = aliasAdapter.getView(it, null, null)
                val params = GridLayout.LayoutParams()
                params.columnSpec = GridLayout.spec(it % 3, 1.0f)
                params.rowSpec = GridLayout.spec(it / 3, GridLayout.BASELINE, 1.0f)
                params.setGravity(Gravity.CENTER)
                item?.layoutParams = params
                aliasView.addView(item)
            }
        }

    }

    private fun populatePastResultsCard(rootView: View) {
        val pastResultsView = rootView.findViewById<LinearLayout>(R.id.linear_team_pastResults)
        val scoreYearArray = ArrayList<ScoreAndYear>()
        val scores = team.Scores
        scores.asSequence()
                .filter { it.value.Rank != 0 && it.key != "2018" }
                .mapTo(scoreYearArray) {
                    ScoreAndYear(it.key, Score(it.value.Points.format(2).toDouble(), it.value.Rank))
                }
        if (scoreYearArray.size == 0) {
            rootView.findViewById<CardView>(R.id.card_team_pastResults).visibility = View.GONE
        } else {
            val listViewAdapter = TeamPastResultsAdapter(context, scoreYearArray)
            (0 until scoreYearArray.size).forEach {
                val item = listViewAdapter.getView(it, null, null)
                if (item != null) {
                    pastResultsView.addView(item)
                }
            }
        }
    }

    private fun populateMembersCard(rootView: View) {
        val gridLayout = rootView.findViewById<GridLayout>(R.id.grid_team_members)
        val members = team.Members
        if (members == null) {
            rootView.findViewById<CardView>(R.id.card_team_members).visibility = View.GONE
        } else {
            val memberNames = members.map { it.Name }
            val listViewAdapter = TeamMembersAdapter(context, memberNames)
            val membersLength = members.size
            (0 until membersLength).forEach {
                val item = listViewAdapter.getView(it, null, null)
                val params = GridLayout.LayoutParams()
                params.columnSpec = GridLayout.spec(it % 3, 1.0f)
                params.rowSpec = GridLayout.spec(it / 3, GridLayout.BASELINE, 1.0f)
                params.setGravity(Gravity.CENTER)
                item?.layoutParams = params
                gridLayout.addView(item)
            }
        }
    }
}