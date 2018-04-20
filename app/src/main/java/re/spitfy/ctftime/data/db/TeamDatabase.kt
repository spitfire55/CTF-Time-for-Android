package re.spitfy.ctftime.data.db

import io.reactivex.Flowable
import re.spitfy.ctftime.data.db.entity.TeamEntity

interface TeamDatabase {

    fun getAllTeams(): Flowable<List<TeamEntity>>
}