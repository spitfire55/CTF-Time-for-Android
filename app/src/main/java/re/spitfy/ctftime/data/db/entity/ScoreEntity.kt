package re.spitfy.ctftime.data.db.entity

import android.arch.persistence.room.ColumnInfo

data class ScoreEntity (
        @ColumnInfo(name = "score_points")
        var points: Double,
        @ColumnInfo(name = "score_rank")
        var rank: Int
)