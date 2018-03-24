package re.spitfy.ctftime.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import re.spitfy.ctftime.activities.MainActivity

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(
        builder: MainActivitySubcomponent.Builder
    ): AndroidInjector.Factory<out Activity>

}