package re.spitfy.ctftime.utils.extensions

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import io.reactivex.Single
import re.spitfy.ctftime.data.Model
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.utils.RxFirestore

fun DocumentReference.observesSnapshot() = RxFirestore.observeDocumentSnapshot(this)
fun DocumentReference.getsSnapshot() = RxFirestore.getDocumentSnapshot(this)
fun DocumentReference.sets(value: Any) = RxFirestore.setDocument(this, value)
fun DocumentReference.deletes() = RxFirestore.deleteDocument(this)

fun CollectionReference.adds(value: Any) = RxFirestore.addDocumentToCollection(this, value)

fun Query.observesSnapshot() = RxFirestore.observeQuerySnapshot(this)
fun Query.getsSnapshot() = RxFirestore.getQuerySnapshot(this)
fun Query.isEmpty() = RxFirestore.isQuerySnapshotEmpty(this)

// this extension function takes the getSingle() function from rxFirestore library and
// adds the document ids to the emitted objects
inline fun <reified T: Model> CollectionReference.getSingleWithIds(): Single<List<T>> {
    return Single.create { emitter ->
        get()
                .addOnSuccessListener {
                    try {
                        emitter.onSuccess(it.documents.mapNotNull {
                            it.toObject(T::class.java)?.withId<T>(it.id)
                        })
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                }.addOnFailureListener { emitter.onError(it) }
    }
}