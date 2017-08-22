package re.spitfy.ctftime.Adapters

import android.content.Context
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import re.spitfy.ctftime.Data.TeamRankData
import re.spitfy.ctftime.R
import re.spitfy.ctftime.ViewHolder.TeamRankViewHolder


class TeamRankingsAdapter(ref: Query?, ctx: Context) :
        FirebaseRecyclerAdapter<TeamRankData, TeamRankViewHolder>
        (TeamRankData::class.java,
                R.layout.team_data_row,
                TeamRankViewHolder::class.java,
                ref)
{
    private val context = ctx
    companion object {
        private const val TAG  = "TeamRankingsAdapter"
    }

    override fun populateViewHolder(viewHolder: TeamRankViewHolder?,
                                    model: TeamRankData?,
                                    position: Int)
    {
        val actualPosition = (this.getRef(position).key.toInt() + 1).toString()
        viewHolder?.itemView?.setOnClickListener {
            Toast.makeText(context,
                    "You clicked on item " + actualPosition,
                    Toast.LENGTH_SHORT).show()
        }
        if (model != null) {
            viewHolder?.bind(model, actualPosition)
        }

    }
}