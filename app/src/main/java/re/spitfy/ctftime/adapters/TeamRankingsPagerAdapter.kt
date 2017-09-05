package re.spitfy.ctftime.adapters

import android.support.v4.app.FragmentManager
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import re.spitfy.ctftime.fragments.TeamRankingsFragment
import re.spitfy.ctftime.R


class TeamRankingsPagerAdapter(ctx: Context, fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    private val context = ctx
    private val yearsArray = context.resources.getStringArray(R.array.ranking_years)

    override fun getCount(): Int {
        return yearsArray.size
    }

    override fun getItem(position: Int): Fragment {
        return TeamRankingsFragment.newInstance(yearsArray[position])
    }

    override fun getPageTitle(position: Int): String {
        return yearsArray[position]
    }
}