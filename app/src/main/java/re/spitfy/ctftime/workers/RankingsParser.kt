package re.spitfy.ctftime.workers

import android.util.Log
import org.jsoup.Jsoup
import re.spitfy.ctftime.data.TeamRankData
import java.io.IOException

class RankingsParser {

    fun parse(year: String, pageNumber: Int) : ArrayList<TeamRankData>{
        val teamRankDataArray = ArrayList<TeamRankData>()
        try {
            val doc = Jsoup.connect("http://ctftime.org/stats/%s?page=%d".format(year, pageNumber)).get()
            val rows = doc.select("table")[0].select("tr")
            rows.map {
                val columns = it.select("td")
                val rank = columns[0].text().toInt()
                val teamName = columns[1].select("a").text()
                val teamLink = columns[1].select("a").attr("href")
                val countryFlagUrl = columns[2].select("img").attr("src")
                val teamScore = columns[3].text().toFloat()
                val teamRankData = TeamRankData(teamName, rank, countryFlagUrl, teamScore)
                teamRankDataArray.add(teamRankData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("RankingsParser", e.message)
        }
        return teamRankDataArray
    }
}