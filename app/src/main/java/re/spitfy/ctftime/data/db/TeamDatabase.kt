package re.spitfy.ctftime.data.db

import android.arch.persistence.room.RoomDatabase
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.dao.*
import re.spitfy.ctftime.data.db.entity.MemberEntity
import re.spitfy.ctftime.data.db.entity.ScoreEntity
import re.spitfy.ctftime.data.db.entity.TeamWithMembers
import re.spitfy.ctftime.data.mapper.toMemberEntities
import re.spitfy.ctftime.data.mapper.toScoreEntities
import re.spitfy.ctftime.data.mapper.toTeamEntities
import re.spitfy.ctftime.data.mapper.toTeamMemberJoinEntities
import javax.inject.Inject

class TeamDatabase @Inject constructor(
        private val database: RoomDatabase,
        private val teamDao: TeamDao,
        private val scoreDao: ScoreDao,
        private val memberDao: MemberDao,
        private val teamMemberJoinDao: TeamMemberJoinDao
) {

    @CheckResult fun getAllTeams(): Flowable<List<TeamWithMembers>> =
            teamMemberJoinDao.getAllTeams()

    @CheckResult fun getAllScores(): Flowable<List<ScoreEntity>> = scoreDao.getAllScores()

    @CheckResult fun getAllMembers(): Flowable<List<MemberEntity>> = memberDao.getAllMembers()

    fun saveTeamData(teams: List<Team>) {
        database.runInTransaction {
            teamDao.clearAndInsert(teams.toTeamEntities())
            scoreDao.clearAndInsert(teams.toScoreEntities())
            memberDao.clearAndInsert(teams.toMemberEntities())
            teamMemberJoinDao.insert(teams.toTeamMemberJoinEntities())

        }
    }
}