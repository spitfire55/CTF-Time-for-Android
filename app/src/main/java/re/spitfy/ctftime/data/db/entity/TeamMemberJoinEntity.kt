package re.spitfy.ctftime.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE

@Entity(
        tableName = "team_member_join",
        primaryKeys = ["teamId", "memberId"],
        foreignKeys = [
            (ForeignKey(
                    entity = TeamEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("teamId"),
                    onDelete = CASCADE
            )),
            (ForeignKey(
                    entity = MemberEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("memberId"),
                    onDelete = CASCADE
            ))
        ]
)
class TeamMemberJoinEntity (
        val teamId: Int,
        val memberId: Int
)