package re.spitfy.ctftime.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import re.spitfy.ctftime.R
import re.spitfy.ctftime.viewmodel.TeamViewModel

class TeamProfileFragment : android.support.v4.app.Fragment() {

    companion object {
        private const val TEAM_ID = "team_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = arguments?.getString(TEAM_ID)
        val model = ViewModelProviders.of(this).get(TeamViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_profile, container, false)
    }
}
