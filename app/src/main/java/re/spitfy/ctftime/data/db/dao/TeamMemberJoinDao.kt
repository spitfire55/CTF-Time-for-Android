package re.spitfy.ctftime.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language
import re.spitfy.ctftime.data.db.entity.TeamMemberJoinEntity
import re.spitfy.ctftime.data.db.entity.TeamWithMembers

@Dao abstract class TeamMemberJoinDao {

    @CheckResult
    @Query("SELECT * FROM team")
    abstract fun getAllTeams(): Flowable<List<TeamWithMembers>>

    @Insert
    abstract fun insert(teamMembersJoin: List<TeamMemberJoinEntity>)
}