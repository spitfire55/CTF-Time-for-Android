package re.spitfy.ctftime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.ScoreAndYear

class TeamPastResultsAdapter(
        private val ctx : Context?,
        scores : ArrayList<ScoreAndYear>
) : ArrayAdapter<ScoreAndYear>(ctx, 0, scores) {

    private class ViewHolder {
        internal var year: TextView? = null
        internal var rank: TextView? = null
        internal var points : TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var lConvertView = convertView
        val viewHolder : ViewHolder
        val score = getItem(position)
        if (score.Score.Rank == 0) {
            return null
        }
        if (lConvertView == null) {
            viewHolder = ViewHolder()
            lConvertView = LayoutInflater.from(ctx).inflate(R.layout.team_scores_row, parent, false)
            viewHolder.year = lConvertView.findViewById(R.id.appCompatText_team_pastScoresYear)
            viewHolder.rank = lConvertView.findViewById(R.id.appCompatText_team_pastScoresRank)
            viewHolder.points = lConvertView.findViewById(R.id.appCompatText_team_pastScoresPoints)
            lConvertView.tag = viewHolder
        } else {
            viewHolder = lConvertView.tag as ViewHolder
        }
        viewHolder.year?.text = score.Year
        viewHolder.rank?.text = score.Score.Rank.toString()
        viewHolder.points?.text = score.Score.Points.toString()

        //Log.d(TAG, viewHolder.year?.text.toString())
        return lConvertView
    }
}