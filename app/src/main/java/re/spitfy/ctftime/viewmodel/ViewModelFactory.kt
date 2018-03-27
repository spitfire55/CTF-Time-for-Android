package re.spitfy.ctftime.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import re.spitfy.ctftime.repo.FirestoreRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
        val repository: FirestoreRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T: ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(TeamViewModel::class.java) ->
                            TeamViewModel(repository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
                }
            } as T
}