package re.spitfy.ctftime.data.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class TeamWithMembers (
        @Embedded val team: TeamEntity,
        @Relation(
                parentColumn = "id",
                entityColumn = "teamId",
                projection = ["memberId"],
                entity = TeamMemberJoinEntity::class
        )
        val memberIdList: List<Int> = emptyList()
)