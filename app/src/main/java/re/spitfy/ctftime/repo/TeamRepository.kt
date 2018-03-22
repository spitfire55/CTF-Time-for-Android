package re.spitfy.ctftime.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.utils.FirestoreFilters
import re.spitfy.ctftime.utils.FirestoreRequestListener
import javax.inject.Inject


class TeamRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository<Team> {

    override fun getDocument(
        collectionPath: String,
        documentPath: String,
        requestListener: FirestoreRequestListener<Team>
    ): LiveData<Team> {
        val team = MutableLiveData<Team>()
        firestore.collection(collectionPath).document(documentPath).get().addOnSuccessListener {
            team.value = it.toObject(Team::class.java)
        }
        return team
    }

    override fun getDocuments(
        collectionPath: String,
        filters: FirestoreFilters,
        requestListener: FirestoreRequestListener<Team>
    ): List<LiveData<Team>> {
        val teams = ArrayList<MutableLiveData<Team>>()
        var query: Query = firestore.collection(collectionPath)
        filters.orderByKeys.forEach {
            query = query.orderBy(it.first, filters.sortDirection)
        }
    }
}
