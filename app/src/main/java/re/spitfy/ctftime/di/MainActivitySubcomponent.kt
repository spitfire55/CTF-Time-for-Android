package re.spitfy.ctftime.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import re.spitfy.ctftime.activities.MainActivity

@Subcomponent
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}