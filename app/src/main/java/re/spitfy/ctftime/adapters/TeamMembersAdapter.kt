package re.spitfy.ctftime.adapters

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.Member

class TeamMembersAdapter(
        private val ctx : Context?,
        scores : List<String>
) : ArrayAdapter<String>(ctx, 0, scores) {

    private class ViewHolder {
        internal var member: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var lConvertView = convertView
        val viewHolder : ViewHolder
        val member = getItem(position)
        if (lConvertView == null) {
            viewHolder = ViewHolder()
            lConvertView = LayoutInflater.from(ctx).inflate(R.layout.team_generic_row, parent, false) as? AppCompatTextView
            viewHolder.member = lConvertView?.findViewById(R.id.appCompatText_team_item)
            lConvertView?.tag = viewHolder
        } else {
            viewHolder = lConvertView.tag as ViewHolder
        }
        viewHolder.member?.text = member
        return lConvertView
    }
}