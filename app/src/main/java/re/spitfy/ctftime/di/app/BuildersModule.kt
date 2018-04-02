package re.spitfy.ctftime.di.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import re.spitfy.ctftime.view.MainActivity
import re.spitfy.ctftime.di.MainActivityModule

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

}