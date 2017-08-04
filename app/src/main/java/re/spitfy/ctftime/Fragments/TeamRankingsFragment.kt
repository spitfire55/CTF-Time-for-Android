package re.spitfy.ctftime.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R

/**
 * Created by spitfire on 7/29/17.
 */
class TeamRankingsFragment: android.support.v4.app.Fragment() {
    var data: Array<TeamRankData>?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acquireRankings()
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val result = inflater?.inflate(
                R.layout.fragment_team_rankings, container, false)


    }

    private fun acquireRankings() {

    }
}