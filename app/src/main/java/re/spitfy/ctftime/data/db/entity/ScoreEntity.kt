package re.spitfy.ctftime.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ForeignKey

data class ScoreEntity (

    val year: String,
    var points: Double,
    var rank: Int
)