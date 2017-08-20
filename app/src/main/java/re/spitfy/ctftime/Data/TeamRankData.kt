package re.spitfy.ctftime.Data

data class TeamRankData (
    val points: Float,
    val team_name: String)
fun TeamRankData() {} //factory method needed for Firebase
