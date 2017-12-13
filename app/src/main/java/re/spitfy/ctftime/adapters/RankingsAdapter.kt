package re.spitfy.ctftime.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import re.spitfy.ctftime.R
import re.spitfy.ctftime.data.TeamRankData
import re.spitfy.ctftime.viewHolder.TeamRankViewHolder
import java.io.IOException

class RankingsAdapter(private val rankings: ArrayList<TeamRankData>)
    : RecyclerView.Adapter<TeamRankViewHolder>()
{
    companion object {
        private val logTag = "RankingsAdapter"
    }
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
        val url = "http://us-central1-ctf-time-for-android.cloudfunctions.net/" +
                "rankings?year=%s&pageNumber=%d".format(year, pageNumber)
        val doc = Jsoup.connect(url).get()
        val rows = doc.select("table")[0].select("tr")
        for (i in 1 until rows.size) {
            val columns = rows[i].select("td")
            val rank = columns[0].text().toInt()
            val teamName = columns[1].select("a").text()
            //val teamLink = columns[1].select("a").attr("href")
            val countryFlagUrl = columns[2].select("img").attr("src")
            val teamScore = columns[3].text().toFloat()
            val teamRankData = TeamRankData(teamName, rank, countryFlagUrl, teamScore)
            rankings.add(teamRankData)
        }
        Log.d("RankingsAdapter", "Finished parsing webpage!")
        this.notifyDataSetChanged()
    }
}