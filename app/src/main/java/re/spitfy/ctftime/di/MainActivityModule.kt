package re.spitfy.ctftime.di

import dagger.Module
import dagger.Provides
import re.spitfy.ctftime.viewmodel.MainActivityViewModel


@Module(subcomponents = [MainActivitySubComponent::class])
abstract class MainActivityModule {

    //TODO: ProvideMainViewModel
    @Provides
    fun provideMainViewModel(): MainActivityViewModel {
        return MainActivityViewModel()
    }
}