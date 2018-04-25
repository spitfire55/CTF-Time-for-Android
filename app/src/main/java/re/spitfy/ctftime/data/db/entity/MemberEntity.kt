package re.spitfy.ctftime.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "member")
data class MemberEntity (
        @PrimaryKey val id: Int,
        val name: String
)