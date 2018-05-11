package re.spitfy.ctftime.data.db.dao

import android.arch.persistence.room.*
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import re.spitfy.ctftime.data.db.entity.ScoreEntity

@Dao
abstract class ScoreDao {

    @CheckResult
    @Query("SELECT * FROM score")
    abstract fun getAllScores(): Flowable<List<ScoreEntity>>

    @Query("DELETE FROM score")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(scores: List<ScoreEntity>)

    @Transaction open fun clearAndInsert(newScores: List<ScoreEntity>) {
        deleteAll()
        insert(newScores)
    }
}