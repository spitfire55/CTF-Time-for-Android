package re.spitfy.ctftime.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jsoup.Jsoup
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.TeamRankData
import re.spitfy.ctftime.viewHolder.TeamRankViewHolder
import java.io.IOException

class RankingsAdapter(private val rankings: ArrayList<TeamRankData>) : RecyclerView.Adapter<TeamRankViewHolder>() {
    override fun getItemCount(): Int {
        return rankings.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TeamRankViewHolder {
        val view = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.team_rankings_row,
                        parent,
                        false)
        return TeamRankViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: TeamRankViewHolder?, position: Int) {
        holder?.bind(rankings[position])
    }

    fun parse(year: String, pageNumber: Int) {
        try {
            val doc = Jsoup.connect("http://ctftime.org/stats/%s?page=%d".format(year, pageNumber+1)).get()
            val rows = doc.select("table")[0].select("tr")
            rows.map {
                val columns = it.select("td")
                val rank = columns[0].text().toInt()
                val teamName = columns[1].select("a").text()
                val teamLink = columns[1].select("a").attr("href")
                val countryFlagUrl = columns[2].select("img").attr("src")
                val teamScore = columns[3].text().toFloat()
                val teamRankData = TeamRankData(teamName, rank, countryFlagUrl, teamScore)
                rankings.add(teamRankData)
            }

        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("RankingsParser", e.message)
        }
        Log.d("RankingsAdapter", "Finished parsing webpage!")
        this.notifyDataSetChanged()
    }
}