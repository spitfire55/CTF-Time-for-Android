package re.spitfy.ctftime.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component
import re.spitfy.ctftime.CTFTimeApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FirestoreModule::class, MainActivityModule::class])
interface ApplicationComponent {

    fun inject(app: CTFTimeApp)

    fun provideContext(): Context
    fun provideFirestore(): FirebaseFirestore

}