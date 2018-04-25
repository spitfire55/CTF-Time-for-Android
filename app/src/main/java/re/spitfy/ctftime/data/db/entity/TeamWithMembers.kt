package re.spitfy.ctftime.data.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class TeamWithMembers (
        @Embedded var team: TeamEntity? = null,
        @Relation(
                parentColumn = "id",
                entityColumn = "teamId",
                projection = ["memberId"],
                entity = TeamMemberJoinEntity::class
        )
        var memberIdList: List<Int> = emptyList()
)