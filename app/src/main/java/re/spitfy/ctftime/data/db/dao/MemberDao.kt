package re.spitfy.ctftime.data.db.dao

import android.arch.persistence.room.*
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import re.spitfy.ctftime.data.db.entity.MemberEntity

@Dao
abstract class MemberDao {

    @CheckResult
    @Query("SELECT * FROM member")
    abstract fun getAllMembers(): Flowable<List<MemberEntity>>

    @Query("DELETE FROM member")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(members: List<MemberEntity>)

    @Transaction open fun clearAndInsert(newMembers: List<MemberEntity>) {
        deleteAll()
        insert(newMembers)
    }
}