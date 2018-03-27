package re.spitfy.ctftime.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Named

class FirestoreFilterFactory {

    @Inject @Named("Teams") lateinit var teamsReference: CollectionReference
    @Inject @Named("Ctfs") lateinit var ctfsReference: CollectionReference
    @Inject @Named("Writeups") lateinit var writeupsReference: CollectionReference


}