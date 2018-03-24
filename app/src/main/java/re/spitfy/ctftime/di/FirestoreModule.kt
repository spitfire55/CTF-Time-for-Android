package re.spitfy.ctftime.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}