package re.spitfy.ctftime.data.db

import android.arch.persistence.room.RoomDatabase
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.dao.MemberDao
import re.spitfy.ctftime.data.db.dao.ScoreDao
import re.spitfy.ctftime.data.db.dao.TeamDao
import re.spitfy.ctftime.data.db.dao.TeamMemberJoinDao
import re.spitfy.ctftime.data.mapper.toMemberEntities
import re.spitfy.ctftime.data.mapper.toScoreEntities
import re.spitfy.ctftime.data.mapper.toTeamEntities
import javax.inject.Inject

class TeamDatabase @Inject constructor(
        private val database: RoomDatabase,
        private val teamDao: TeamDao,
        private val scoreDao: ScoreDao,
        private val memberDao: MemberDao,
        private val aliasDao: AliasDao,
        private val teamMemberJoinDao: TeamMemberJoinDao
){

    fun saveTeamData(teams: List<Team>) {
        database.runInTransaction {
            teamDao.clearAndInsert(teams.toTeamEntities())
            scoreDao.clearAndInsert(teams.toScoreEntities())
            memberDao.clearAndInsert(teams.toMemberEntities())
            aliasDao.clearAndInsert(teams.toAliasEntities())
            teamMemberJoinDao.insert(teams.toTeamMemberJoinEntities())

        }
    }
}