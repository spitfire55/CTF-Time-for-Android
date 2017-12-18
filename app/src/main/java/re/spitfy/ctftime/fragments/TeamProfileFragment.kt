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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class TeamProfileFragment : android.support.v4.app.Fragment()
{
    private var teamId = 0

    companion object
    {
        val TAG = "TeamProfileFragment"
        //private lateinit var teamNames : MutableList<String>

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

        FirebaseFirestore.getInstance()
                .collection("Teams")
                .get()
                .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(task: Task<QuerySnapshot>) {
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                val nameStr = document.getString("Name")
                                if (nameStr != null) {
                                    autoCompleteAdapter.add(nameStr)
                                }
                            }
                        }
                    }
                })
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