package re.spitfy.ctftime.data



class Team(
        val CountryCode : String = "",
        val Rank: Int = 0,
        val Scores: Map<String, Float> = emptyMap(),
        val Name: String = ""
)

