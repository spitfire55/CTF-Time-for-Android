package re.spitfy.ctftime.data.db.dao

import android.arch.persistence.room.*
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import re.spitfy.ctftime.data.db.entity.TeamEntity

@Dao
abstract class TeamDao {

    @CheckResult
    @Query("SELECT * FROM team")
    abstract fun getAllTeams(): Flowable<List<TeamEntity>>

    @Query("DELETE FROM team")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(teams: List<TeamEntity>)

    @Transaction open fun clearAndInsert(newTeams: List<TeamEntity>) {
        deleteAll()
        insert(newTeams)
    }
}