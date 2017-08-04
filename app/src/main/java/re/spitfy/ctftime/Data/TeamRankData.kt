package re.spitfy.ctftime.Data

/**
 * Created by spitfire on 7/30/17.
 */
data class TeamRankData(
    val rank: Int = 1,
    val name: String = "BytesForEveryone",
    val country: String? = null,
    val points: Float = 0.0f,
    val events: Int = 0)