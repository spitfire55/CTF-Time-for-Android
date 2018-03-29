package re.spitfy.ctftime.repo

import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject
import javax.inject.Named

class TeamRepository {

    @Inject @Named("Teams")
    lateinit var teamCollection: CollectionReference

    fun
}