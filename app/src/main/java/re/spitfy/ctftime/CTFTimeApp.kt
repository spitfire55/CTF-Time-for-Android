package re.spitfy.ctftime

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import re.spitfy.ctftime.di.AppModule
import re.spitfy.ctftime.di.DaggerApplicationComponent
import re.spitfy.ctftime.di.FirestoreModule
import javax.inject.Inject

class CTFTimeApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .firestoreModule(FirestoreModule())
                .build()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}