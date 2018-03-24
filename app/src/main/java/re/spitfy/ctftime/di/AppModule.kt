package re.spitfy.ctftime.di

import android.content.Context
import dagger.Module
import dagger.Provides
import re.spitfy.ctftime.CTFTimeApp
import javax.inject.Singleton

@Module(subcomponents = [MainActivitySubcomponent::class])
class AppModule(val app: CTFTimeApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app
}