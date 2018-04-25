package re.spitfy.ctftime.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
open class Model {
    @Exclude
    var id: Int? = null

    fun <T: Model> withId(id: Int): T {
        this.id = id
        @Suppress("UNCHECKED_CAST")
        return this as T
    }
}