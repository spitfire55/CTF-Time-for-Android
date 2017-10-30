package re.spitfy.ctftime.data



class TeamRankData(val Academic : Boolean = false,
                   val Aliases : List<String> = emptyList(),
                   val Country: String = "",
                   val Id: Int = 0,
                   val Name: String = "",
                   val Ratings: Map<String, RatingData> = emptyMap())

