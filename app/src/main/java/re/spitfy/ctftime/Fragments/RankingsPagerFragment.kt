package re.spitfy.ctftime.Fragments

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import re.spitfy.ctftime.Adapters.TeamRankingsPagerAdapter
import re.spitfy.ctftime.R


class RankingsPagerFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(
                R.layout.fragment_rankings_pager,
                container,
                false)
        val pager = rootView?.findViewById<ViewPager>(R.id.rankingsPager)
        pager?.adapter = TeamRankingsPagerAdapter(activity, childFragmentManager)

        return rootView
    }
}