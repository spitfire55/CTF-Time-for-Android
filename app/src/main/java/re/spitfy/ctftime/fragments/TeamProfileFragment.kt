package re.spitfy.ctftime.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import re.spitfy.ctftime.R
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.appbar_main.*
import re.spitfy.ctftime.data.Team
import java.io.Serializable


class TeamProfileFragment : android.support.v4.app.Fragment()
{
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var team : Team? = null
    private lateinit var autoCompleteTextView : AutoCompleteTextView
    private lateinit var autoCompleteProgressBar : ProgressBar
    private var teamNameArray : MutableList<String> = ArrayList()
    private var teamArray : MutableList<Team> = ArrayList()

    companion object {
        const val TAG = "TeamProfileFragment"
        const val SUGGESTION_LIMIT : Long = 3
        fun newInstance(team: Team?): TeamProfileFragment {
            val args = Bundle()
            args.putSerializable("Team", team as? Serializable)
            val fragment = TeamProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teamArg = arguments?.getSerializable("Team") as? Team
        if (teamArg != null) {
            team = teamArg
        } else {
            //TODO: Setup Fragment to load dummy frame on first load
        }
        //TODO: Check for internet connectivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_team_profile, container, false)
        rootView?.tag = TAG + team?.Name
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.toolbar?.title = "Team Profile"
        autoCompleteTextView = view.findViewById(R.id.appCompatAutoCompleteTextView_team_searchTeam)
        autoCompleteProgressBar = view.findViewById(R.id.progressBar_team_searchTeamStatus)
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.hint = ""
            autoCompleteTextView.isCursorVisible = true
        }
        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, _, pos, _ ->
                val newTeamName = adapterView?.getItemAtPosition(pos).toString()
                if (newTeamName != team?.Name) {
                    activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(
                                    R.id.container,
                                    TeamProfileFragment.newInstance(teamArray[pos]),
                                    newTeamName
                            )
                            ?.commit()
                }
        }
        autoCompleteTextView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("TEST", teamNameArray[position])
                val newTeamName = parent?.getItemAtPosition(position).toString()
                if (newTeamName != team?.Name) {
                    activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(
                                    R.id.container,
                                    TeamProfileFragment.newInstance(teamArray[position]),
                                    newTeamName
                            )
                            ?.commit()
                }
            }
        }
        setAutoCompleteListener()
        if (team != null) {
            populateGeneralCard(view)
        }
    }

    override fun onDetach() {
        super.onDetach()
        val inputManager = activity?.
                getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setAutoCompleteListener() {
        autoCompleteTextView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSequence != null && charSequence.isNotBlank()) {
                    autoCompleteTextView.dismissDropDown()
                    autoCompleteProgressBar.visibility = View.VISIBLE
                    retrieveTeamNameSuggestions(charSequence.toString().trim().toLowerCase())
                    Log.d(TAG, charSequence.toString().toLowerCase())
                } else {
                    autoCompleteTextView.dismissDropDown()
                    autoCompleteProgressBar.visibility = View.INVISIBLE
                }
            }
            override fun afterTextChanged(input: Editable?) {}
        })
    }

    private fun retrieveTeamNameSuggestions(input: String) {
        //TODO: Upper case vs lower case teams, caching results by searching upon installation
        db.collection("Teams")
                .orderBy("NameCaseInsensitive", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("NameCaseInsensitive", input)
                .whereLessThanOrEqualTo("NameCaseInsensitive", input + "\uf8ff")
                .limit(SUGGESTION_LIMIT)
                .get()
                .addOnCompleteListener {
                    task -> if (task.isSuccessful) {
                        val querySnapshot = task.result
                        if (!querySnapshot.isEmpty) {
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

    }

    private fun populateGeneralCard(rootView : View) {
        rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_pointsDescription).text = resources.getString(R.string.points)
        rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_rankDescription).text = resources.getString(R.string.rank)
        rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_name).text = team?.Name
        rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_pointsValue).text = team?.Scores?.get("2017")?.Points?.toString()
        rootView.findViewById<AppCompatTextView>(R.id.appCompatText_team_rankValue).text = team?.Scores?.get("2017")?.Rank?.toString()
        Picasso.with(context)
                .load("https://ctftime.org/${team?.Logo}")
                .into(rootView.findViewById<ImageView>(R.id.image_team_logo))
    }
}