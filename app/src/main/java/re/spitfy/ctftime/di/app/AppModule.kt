package re.spitfy.ctftime.di.app

import android.app.Application
import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import re.spitfy.ctftime.di.MainActivitySubComponent
import javax.inject.Named
import javax.inject.Singleton

@Module(subcomponents = [MainActivitySubComponent::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Named("Teams")
    @Singleton
    fun provideFirestoreTeams(): CollectionReference {
        FirebaseFirestore.setLoggingEnabled(true)
        return FirebaseFirestore.getInstance().collection("Teams")

    }

    @Provides
    @Named("Writeups")
    @Singleton
    fun provideFirestoreWriteups(): CollectionReference {
        FirebaseFirestore.setLoggingEnabled(true)
        return FirebaseFirestore.getInstance().collection("Writeups")

    }

    @Provides
    @Named("Ctfs")
    @Singleton
    fun provideFirestoreCtfs(): CollectionReference {
        FirebaseFirestore.setLoggingEnabled(true)
        return FirebaseFirestore.getInstance().collection("Ctfs")

    }
}