package re.spitfy.ctftime.data

import android.arch.lifecycle.LiveData
import com.google.firebase.firestore.*

class DocumentLiveData<T>(
    private val ref: DocumentReference,
    private val type: Class<T>
): LiveData<Resource<T>>(), EventListener<DocumentSnapshot> {

    private var registration: ListenerRegistration? = null

    override fun onEvent(snapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        value = if (e != null) Resource(e) else Resource(snapshot?.toObject(type))
    }

    override fun onActive() {
        super.onActive()
        registration = ref.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        registration?.remove()
        registration = null
    }
}