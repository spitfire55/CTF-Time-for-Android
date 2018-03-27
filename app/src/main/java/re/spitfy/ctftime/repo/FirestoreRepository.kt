package re.spitfy.ctftime.repo

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import re.spitfy.ctftime.utils.FirestoreFilters
import re.spitfy.ctftime.utils.FirestoreRequestListener

interface FirestoreRepository {

    fun getDocument(documentPath: String,
                    requestListener: FirestoreRequestListener<AndroidViewModel>
    ): LiveData<AndroidViewModel>

    fun getDocuments(collectionPath: String,
                     filters: FirestoreFilters,
                     requestListener: FirestoreRequestListener<AndroidViewModel>
    ): List<LiveData<AndroidViewModel>>

    fun updateDocument(documentPath: String, data: AndroidViewModel)

}