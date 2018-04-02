package re.spitfy.ctftime.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Model {
    @Exclude
    var id: String? = null

    fun <T> withId(id: String): T {
        this.id = id
        return this as T
    }
}