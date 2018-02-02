package re.spitfy.ctftime.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.utils.format

class TopTenTeamsAdapter(
        private val ctx : Context?,
        teams : List<Team>
): ArrayAdapter<Team>(ctx, 0, teams) {

    private class ViewHolder {
        internal var rankView: TextView? = null
        internal var nameView: TextView? = null
        internal var countryView: ImageView? = null
        internal var pointsView: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var lConvertView = convertView
        val viewHolder: ViewHolder
        val team = getItem(position)
        if (lConvertView == null) {
            viewHolder = ViewHolder()
            lConvertView = LayoutInflater.from(ctx).inflate(R.layout.team_rankings_row, parent, false)
            viewHolder.rankView = lConvertView.findViewById(R.id.appCompatText_rankingsRow_rank)
            viewHolder.nameView = lConvertView.findViewById(R.id.appCompatText_rankingsRow_teamName)
            viewHolder.countryView = lConvertView.findViewById(R.id.image_rankingsRow_country)
            viewHolder.pointsView = lConvertView.findViewById(R.id.appCompatText_rankingsRow_points)
            lConvertView.tag = viewHolder
        } else {
            viewHolder = lConvertView.tag as ViewHolder
        }

        viewHolder.rankView?.text = (position + 1).toString()
        viewHolder.nameView?.text = team.Name
        Picasso.with(ctx)
                .load("https://ctftime.org/static/images/f/" +
                        "${team.CountryCode.toLowerCase()}.png"
                )
                .into(viewHolder.countryView)
        viewHolder.pointsView?.text = team.Scores["2018"]?.Points?.format(3)
        return lConvertView
    }
}