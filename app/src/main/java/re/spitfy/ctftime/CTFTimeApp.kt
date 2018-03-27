package re.spitfy.ctftime

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import re.spitfy.ctftime.di.app.DaggerApplicationComponent
import javax.inject.Inject

class CTFTimeApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().build().inject(this)
        Fabric.with(this, Crashlytics())
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}