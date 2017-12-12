package re.spitfy.ctftime.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import re.spitfy.ctftime.R
import android.util.Log
import android.widget.ArrayAdapter


class TeamProfileFragment : android.support.v4.app.Fragment()
{
    //TODO: Ensure checks for id != 0 whenever accessed
    private var teamId = 0

    companion object
    {
        val TAG = "TeamProfileFragment"

        fun newInstance(id: Int): TeamProfileFragment
        {
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
            Log.d(TAG, "No arguments. Did you create TeamProfileFragment " +
                    "instance with newInstance method?")
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val rootView = inflater.inflate(
                R.layout.fragment_team_profile,
                container,
                false)
        rootView?.tag = TAG + id

        val autoCompleteView = rootView?.findViewById<AutoCompleteTextView>(R.id.team_search_bar)
        autoCompleteView?.setOnClickListener {
            autoCompleteView.hint = ""
            autoCompleteView.isCursorVisible = true
        }
        val autoCompleteDropdown = android.R.layout.simple_dropdown_item_1line
        val autoCompleteAdapter = ArrayAdapter<String>(activity, autoCompleteDropdown)
        autoCompleteView?.setAdapter(autoCompleteAdapter)
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        val inputManager = activity?.
                getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }
}