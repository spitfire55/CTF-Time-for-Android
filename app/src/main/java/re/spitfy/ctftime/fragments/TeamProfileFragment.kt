package re.spitfy.ctftime.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.util.Base64
import re.spitfy.ctftime.R
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class TeamProfileFragment : android.support.v4.app.Fragment()
{
    //TODO: Ensure checks for id != 0 whenever accessed
    private var teamId = 0

    companion object
    {
        val TAG = "TeamProfileFragment"
        var teamNames = emptyList<String>()

        fun newInstance(id: Int): TeamProfileFragment
        {
            val args = Bundle()
            args.putInt("ID", id)
            val fragment = TeamProfileFragment()
            fragment.arguments = args
            return fragment
        }

        fun getTeamNameList() {
            val fb = FirebaseDatabase.getInstance().getReference("TeamsByName")
            fb.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(p0: DataSnapshot?) {
                    val teamNamesSnapshot = p0?.children
                    val teamMap = teamNamesSnapshot?.associateBy({it.key}, {it.value})
                    val teamNames = teamMap?.keys?.map {
                        it -> Base64.decode(it, Base64.URL_SAFE).toString()
                    }?.toList()
                    if (teamNames != null) {
                        Companion.teamNames = teamNames
                    }
                }

                override fun onCancelled(p0: DatabaseError?) {
                    Log.e(TAG, "Failed to get team names." + p0.toString())
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val idArg = arguments.getInt("ID")
            teamId = idArg
        } catch (e : NullPointerException) {
            Log.d(TAG, "No arguments. Did you create TeamProfileFragment " +
                    "instance with newInstance method?")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val rootView = inflater?.inflate(
                R.layout.fragment_team_profile,
                container,
                false)
        rootView?.tag = TAG + id

        val autoCompleteView = rootView?.findViewById<AutoCompleteTextView>(R.id.team_search_bar)
        autoCompleteView?.setOnClickListener {
            autoCompleteView.hint = ""
            autoCompleteView.isCursorVisible = true
        }
        autoCompleteView?.dropDownHeight = 5
        val autoCompleteDropdown = android.R.layout.simple_dropdown_item_1line
        val autoCompleteAdapter = ArrayAdapter<String>(activity, autoCompleteDropdown, teamNames)
        autoCompleteView?.setAdapter(autoCompleteAdapter)

        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        val inputManager = activity.
                getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                activity.currentFocus.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }


}