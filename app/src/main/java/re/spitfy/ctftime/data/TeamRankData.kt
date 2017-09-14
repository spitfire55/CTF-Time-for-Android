package re.spitfy.ctftime.data



class TeamRankData(val academic : Boolean = false,
                   val country: String = "",
                   val name: String = "",
                   val rating: Map<String, RatingData> = emptyMap())

