package re.spitfy.ctftime.data

import android.arch.lifecycle.LiveData
import com.google.firebase.firestore.*
import re.spitfy.ctftime.viewobject.Resource

class QueryLiveData<T: Model>(
    private val query: Query,
    private val type: Class<T>
): LiveData<Resource<List<T>>>(), EventListener<QuerySnapshot> {

    private var registration: ListenerRegistration? = null

    override fun onEvent(snapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
        value = if (e != null) Resource(e) else Resource(documentToList(snapshots))
    }

    override fun onActive() {
        super.onActive()
        registration = query.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        registration?.remove()
        registration = null
    }

    private fun documentToList(snapshots: QuerySnapshot?): List<T> {
        val retList: MutableList<T> = ArrayList()
        if(snapshots != null && !snapshots.isEmpty) {
            snapshots.documents.forEach{
                retList.add(it.toObject(type).withId(it.id))
            }
        }
        return retList
    }
}