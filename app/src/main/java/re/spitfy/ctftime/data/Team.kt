package re.spitfy.ctftime.data

import java.io.Serializable

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
        val Name: String = "",
        val NameCaseInsensitive: String = "",
        val OtherLinks : List<String> = emptyList(),
        val Scores: Map<String, Score> = emptyMap(),
        val Skype: String = "",
        val Telegram : String = "",
        val Twitter: String = "",
        val Website : String = ""
) : Serializable

