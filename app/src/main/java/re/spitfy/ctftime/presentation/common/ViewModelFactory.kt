package re.spitfy.ctftime.presentation.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import re.spitfy.ctftime.data.repo.FirestoreRepository
import re.spitfy.ctftime.presentation.team.TeamViewModel
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