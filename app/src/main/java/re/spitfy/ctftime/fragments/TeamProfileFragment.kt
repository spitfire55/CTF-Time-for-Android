package re.spitfy.ctftime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import re.spitfy.ctftime.R


class TeamProfileFragment : android.support.v4.app.Fragment()
{
    //TODO: Ensure checks for id != 0 whenever accessed
    private var teamId = 0

    companion object
    {
        val TAG = "TeamProfileFragment"

        /*fun newInstance(id: Int): TeamProfileFragment
        {
            val args = Bundle()
            args.putInt("ID", id)
            val fragment = TeamProfileFragment()
            fragment.arguments = args
            return fragment
        }*/
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idArg = arguments.getInt("ID")
        if (idArg != 0) {
            teamId = idArg
        } else {
            Log.d(TAG, "No arguments. Did you create TeamProfileFragment " +
                    "instance with newInstance method?")
        }
    }*/

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val rootView = inflater?.inflate(
                R.layout.fragment_team_profile,
                container,
                false)
        rootView?.tag = TAG + id
        return rootView
    }
}