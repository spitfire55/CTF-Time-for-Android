package re.spitfy.ctftime.data.repo

import android.support.annotation.CheckResult
import io.reactivex.Completable
import re.spitfy.ctftime.data.db.TeamDatabase
import re.spitfy.ctftime.data.firestore.TeamFirestoreApi
import re.spitfy.ctftime.utils.SchedulerProvider
import javax.inject.Inject

class TeamRepository @Inject constructor(
        private val teamApi: TeamFirestoreApi,
        private val teamDatabase: TeamDatabase,
        private val schedulerProvider: SchedulerProvider)
{

    @CheckResult fun refreshTeams(): Completable {
        return teamApi.teams
                .doOnSuccess { teams ->
                    teamDatabase.saveTeamData(teams)
                }
                .subscribeOn(schedulerProvider.computation())
                .toCompletable()
    }

}