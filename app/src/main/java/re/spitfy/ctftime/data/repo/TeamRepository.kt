package re.spitfy.ctftime.data.repo

import android.support.annotation.CheckResult
import io.reactivex.Completable
import io.reactivex.Flowable
import re.spitfy.ctftime.data.Member
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.TeamDatabase
import re.spitfy.ctftime.data.firestore.TeamFirestoreApi
import re.spitfy.ctftime.data.mapper.toMember
import re.spitfy.ctftime.data.mapper.toTeam
import re.spitfy.ctftime.data.mapper.toTeams
import re.spitfy.ctftime.utils.SchedulerProvider
import javax.inject.Inject

class TeamRepository @Inject constructor(
        private val teamApi: TeamFirestoreApi,
        private val teamDatabase: TeamDatabase,
        private val schedulerProvider: SchedulerProvider)
{
    val members: Flowable<List<Member>> = teamDatabase.getAllMembers().map {
        it.map { memberEntity ->
            memberEntity.toMember()
        }
    }

    val teams: Flowable<List<Team>> = teamDatabase.getAllTeams().toTeams(members)

    @CheckResult fun refreshTeams(): Completable {
        return teamApi.teams
                .doOnSuccess { teams ->
                    teamDatabase.saveTeamData(teams)
                }
                .subscribeOn(schedulerProvider.computation())
                .toCompletable()
    }
}