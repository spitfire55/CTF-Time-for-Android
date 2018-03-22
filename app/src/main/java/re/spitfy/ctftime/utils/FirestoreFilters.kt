package re.spitfy.ctftime.utils

import com.google.firebase.firestore.Query

class FirestoreFilters {

    val orderByKeys = ArrayList<Pair<String, Any>>()
    val greaterThanKeys = ArrayList<Pair<String, Any>>()
    val lesserThanKeys = ArrayList<Pair<String, Any>>()
    val equalToKeys = ArrayList<Pair<String, Any>>()
    var sortDirection: Query.Direction = Query.Direction.ASCENDING

}