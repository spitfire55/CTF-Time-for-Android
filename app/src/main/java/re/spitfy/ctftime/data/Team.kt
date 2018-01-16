package re.spitfy.ctftime.data



data class Team(
        val Academic : String = "",
        val Aliases : List<String> = emptyList(),
        val CountryCode : String = "",
        val Description : String = "",
        val Email : String = "",
        val Hash : String = "",
        val ICQ : String = "",
        val Jabber : String = "",
        val LinkedIn : String = "",
        val Logo : String = "",
        val Members : List<Member> = emptyList(),
        val Scores: Map<String, Float> = emptyMap(),
        val Name: String = ""
)

