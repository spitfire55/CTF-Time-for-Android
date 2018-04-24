package re.spitfy.ctftime.data.db

import android.arch.persistence.room.RoomDatabase
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.dao.TeamDao
import re.spitfy.ctftime.data.mapper.toTeamEntities
import javax.inject.Inject

class TeamDatabase @Inject constructor(
        private val database: RoomDatabase,
        private val teamDao: TeamDao
){

    fun saveTeams(teams: List<Team>) {
        database.runInTransaction {
            teamDao.clearAndInsert(teams.toTeamEntities())
        }
    }
}