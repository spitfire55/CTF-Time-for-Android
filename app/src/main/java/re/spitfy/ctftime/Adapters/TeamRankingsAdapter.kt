package re.spitfy.ctftime.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R


class TeamRankingsAdapter(teamData: Array<TeamRankData>, val td: Array<TeamRankData> = teamData) :
        RecyclerView.Adapter<TeamRankingsAdapter.ViewHolder>() {
    companion object {
        private const val TAG  = "TeamRankingsAdapter"
    }

    class ViewHolder(teamDataView: View, val rankView: TextView,
                     val countryView: TextView, val nameView: TextView,
                     val pointsView: TextView) : RecyclerView.ViewHolder(teamDataView) {
        init {
            teamDataView.setOnClickListener(View.OnClickListener {
                Log.w(TAG, "Need to implement onClickListener for team data")
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val teamDataView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.team_data_row, parent, false)
        return ViewHolder(teamDataView,
                teamDataView.findViewById(R.id.team_rank_textview),
                teamDataView.findViewById(R.id.team_country_textview),
                teamDataView.findViewById(R.id.team_name_textview),
                teamDataView.findViewById(R.id.team_points_textview))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.rankView?.text = td[position].rank.toString()
        holder?.countryView?.text = td[position].country
        holder?.nameView?.text =  td[position].name
        holder?.pointsView?.text = td[position].points.toString()
    }

    override fun getItemCount(): Int { return td.size }
}