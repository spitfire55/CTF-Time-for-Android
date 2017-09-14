package re.spitfy.ctftime.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import re.spitfy.ctftime.data.TeamRankData
import re.spitfy.ctftime.R
import re.spitfy.ctftime.viewHolder.TeamRankViewHolder


class TeamRankingsAdapter(val year: String, ref : Query?, ctx: Context) :
        FirebaseRecyclerAdapter<TeamRankData, TeamRankViewHolder>(
                TeamRankData::class.java,R.layout.team_data_row,
                TeamRankViewHolder::class.java, ref
        )
{
    private val context = ctx
    companion object {
        private const val TAG  = "TeamRankingsAdapter"
    }

    override fun populateViewHolder(viewHolder: TeamRankViewHolder?,
                                    model: TeamRankData?,
                                    position: Int)
    {
        if (model != null) {
            viewHolder?.bind(model, year)
        }

    }
}