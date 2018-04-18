package re.spitfy.ctftime.repo

import com.google.firebase.firestore.CollectionReference
import io.reactivex.Flowable
import re.spitfy.ctftime.data.Team
import javax.inject.Inject

class TeamRepository @Inject constructor(val teamCollection: CollectionReference) {

    val teams: Flowable<List<Team>> = Flowable.

    val topTenTeams: Flowable<Map<String, List<Team>>> =
            teams.map { teamList ->
                teamList.filter
    }

}