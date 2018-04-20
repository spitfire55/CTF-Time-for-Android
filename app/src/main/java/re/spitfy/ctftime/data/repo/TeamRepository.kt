package re.spitfy.ctftime.data.repo

import io.reactivex.Flowable
import re.spitfy.ctftime.data.Team
import javax.inject.Inject

class TeamRepository @Inject constructor(private val teamsDatabase: TeamsDatabase) {

    val teams: Flowable<List<Team>> = teamsDatabase.getAllTeams()
    val topTenTeams: Flowable<Map<String, List<Team>>> =
            teams.map { teamList ->
                teamList.filter
    }

}