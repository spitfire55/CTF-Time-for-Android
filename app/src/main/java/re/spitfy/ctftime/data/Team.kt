package re.spitfy.ctftime.data

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Team(
        val Academic : String,
        val Aliases : List<String>,
        val CountryCode : String,
        val Description : String,
        val Email : String,
        val ICQ : String,
        val Jabber : String,
        val LinkedIn : String,
        val Logo : String,
        val Members : List<Member>,
        val Name: String,
        val NameCaseInsensitive: String,
        val OtherLinks : List<String>,
        val Scores: Map<String, Score>,
        val Skype: String,
        val Telegram : String,
        val Twitter: String,
        val Website : String
): Model()

