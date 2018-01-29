package re.spitfy.ctftime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import re.spitfy.ctftime.R

class TeamAliasesAdapter(
    private val ctx : Context?,
    aliases : List<String>
    ) : ArrayAdapter<String>(ctx, 0, aliases) {

    private class ViewHolder {
        internal var alias: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var lConvertView = convertView
        val viewHolder : ViewHolder
        val member = getItem(position)
        if (lConvertView == null) {
            viewHolder = ViewHolder()
            lConvertView = LayoutInflater.from(ctx).inflate(R.layout.team_generic_row, parent, false)
            viewHolder.alias = lConvertView?.findViewById(R.id.appCompatText_team_item)
            lConvertView.tag = viewHolder
        } else {
            viewHolder = lConvertView.tag as ViewHolder
        }

        viewHolder.alias?.text = member
        return lConvertView
    }
}