package re.spitfy.ctftime.repo

import com.google.firebase.firestore.CollectionReference
import re.spitfy.ctftime.data.DocumentLiveData
import re.spitfy.ctftime.data.QueryLiveData
import re.spitfy.ctftime.data.Team
import javax.inject.Inject
import javax.inject.Named

class TeamRepository {

    @Inject @Named("Teams")
    lateinit var teamCollection: CollectionReference

    fun team(id: String?): DocumentLiveData<Team>? {
        if (id == null)
            return null
        val teamRef = teamCollection.document(id)
        val data: DocumentLiveData<Team> = DocumentLiveData(teamRef, Team::class.java)
        teamRef.addSnapshotListener(data)
        return data
    }

    fun topTenTeams(): QueryLiveData<Team>? {
        return QueryLiveData(teamCollection.orderBy("Scores/2018").limit(10), Team::class.java)
    }
}