package re.spitfy.ctftime.utils

import android.support.annotation.CheckResult
import com.google.firebase.firestore.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import re.spitfy.ctftime.utils.extensions.toCompletable

object RxFirestore {

    @CheckResult
    fun observeDocumentSnapshot(ref: DocumentReference): Observable<DocumentSnapshot> {
        return Observable.create<DocumentSnapshot> { emitter ->
            val listener = ref.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    emitter.onError(error)
                } else {
                    // only error or snapshot can be null, not both
                    // safe to ignore snapshot == null if error == null
                    emitter.onNext(snapshot!!)
                }
            }
            emitter.setCancellable { listener.remove() }
        }
    }

    @CheckResult
    fun getDocumentSnapshot(ref: DocumentReference): Single<DocumentSnapshot> {
        return observeDocumentSnapshot(ref).take(1).singleOrError()
    }

    @CheckResult
    fun setDocument(ref: DocumentReference, value: Any): Completable {
        return Completable.defer { ref.set(value).toCompletable() }
    }

    @CheckResult
    fun deleteDocument(ref: DocumentReference): Completable {
        return Completable.defer { ref.delete().toCompletable() }
    }

    @CheckResult
    fun addDocumentToCollection(ref: CollectionReference, value: Any): Completable {
        return Completable.defer { ref.add(value).toCompletable() }
    }

    @CheckResult
    fun observeQuerySnapshot(ref: Query): Observable<QuerySnapshot> {
        return Observable.create { emitter ->
            val listener = ref.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    emitter.tryOnError(error)
                } else {
                    // only error or snapshot can be null, not both
                    // safe to ignore snapshot == null if error == null
                    emitter.onNext(snapshot!!)
                }
            }
            emitter.setCancellable { listener.remove() }
        }
    }

    @CheckResult
    fun getQuerySnapshot(ref: Query): Single<QuerySnapshot> {
        return observeQuerySnapshot(ref).take(1).singleOrError()
    }

    @CheckResult
    fun isQuerySnapshotEmpty(ref: Query): Single<Boolean> {
        return getQuerySnapshot(ref).map { it.isEmpty }
    }
}