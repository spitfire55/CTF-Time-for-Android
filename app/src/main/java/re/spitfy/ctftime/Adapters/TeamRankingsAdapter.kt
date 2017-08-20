package re.spitfy.ctftime.Adapters

import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R
import re.spitfy.ctftime.ViewHolder.TeamRankViewHolder


class TeamRankingsAdapter(ref: Query?) :
        FirebaseRecyclerAdapter<TeamRankData, TeamRankViewHolder>
        (TeamRankData::class.java, R.layout.team_data_row, TeamRankViewHolder::class.java, ref) {

    companion object {
        private const val TAG  = "TeamRankingsAdapter"
    }

    override fun populateViewHolder(viewHolder: TeamRankViewHolder?, model: TeamRankData?, position: Int) {
        viewHolder?.itemView?.setOnClickListener {
            Toast.makeText(null,
                    "You clicked on item "+position.toString(), Toast.LENGTH_SHORT).show()
        }
        if (model != null) {
            viewHolder?.bind(position, model)
        }

    }
}