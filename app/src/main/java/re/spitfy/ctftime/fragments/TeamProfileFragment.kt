package re.spitfy.ctftime.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import re.spitfy.ctftime.R
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.appbar_main.*


class TeamProfileFragment : android.support.v4.app.Fragment()
{
    private var teamId = 0
    private lateinit var db : FirebaseFirestore
    private lateinit var autoCompleteTextView : AutoCompleteTextView
    private lateinit var autoCompleteProgressBar : ProgressBar
    private var teamNameArray : MutableList<String> = ArrayList()

    companion object {
        const val TAG = "TeamProfileFragment"
        const val SUGGESTION_LIMIT : Long = 3
        fun newInstance(id: Int): TeamProfileFragment {
            val args = Bundle()
            args.putInt("ID", id)
            val fragment = TeamProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val idArg = arguments?.getInt("ID")
            if (idArg != null) {
                teamId = idArg
            }
        } catch (e : NullPointerException) {
            Log.d(
                    TAG,
                    "No arguments. Did you create TeamProfileFragment " +
                            "instance with newInstance method?")
        }
        //TODO: Check for internet connectivity
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_team_profile,
                container,
                false
        )
        rootView?.tag = TAG + id

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.toolbar?.title = "Team Profile"
        autoCompleteTextView = view.findViewById(R.id.team_search_text_view)
        autoCompleteProgressBar = view.findViewById(R.id.team_search_progressbar)
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.hint = ""
            autoCompleteTextView.isCursorVisible = true
        }

        setAutoCompleteListener()
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
                    retrieveTeamNameSuggestions(charSequence.toString().toLowerCase())
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
                            teamNameArray.clear()
                            for (document in querySnapshot.documents) {
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
                            } else {
                                Log.d(TAG, "Null activity")
                            }
                        }
                    }
                }

    }
}