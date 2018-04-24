package re.spitfy.ctftime.data.firestore

import com.google.firebase.firestore.CollectionReference
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.utils.extensions.getSingleWithIds
import javax.inject.Inject
import javax.inject.Named

class TeamFirestoreApi @Inject constructor(
        @Named("team") teamReference: CollectionReference
) {
    val teams = teamReference.getSingleWithIds<Team>()
}