package re.spitfy.ctftime.repo

import android.arch.lifecycle.LiveData
import re.spitfy.ctftime.utils.FirestoreFilters
import re.spitfy.ctftime.utils.FirestoreRequestListener

interface FirestoreRepository<FirestoreModel> {

    fun getDocument(documentPath: String,
                    requestListener: FirestoreRequestListener<FirestoreModel>
    ): LiveData<FirestoreModel>

    fun getDocuments(collectionPath: String,
                     filters: FirestoreFilters,
                     requestListener: FirestoreRequestListener<FirestoreModel>
    ): List<LiveData<FirestoreModel>>

    fun updateDocument(documentPath: String, data: FirestoreModel)

}