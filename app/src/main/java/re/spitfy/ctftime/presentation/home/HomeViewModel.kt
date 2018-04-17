package re.spitfy.ctftime.presentation.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.presentation.Result
import re.spitfy.ctftime.repo.TeamRepository
import re.spitfy.ctftime.utils.SchedulerProvider
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val repository: HomeRepository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val topTeams: Map<String, LiveData<Result<List<Team>>>> by lazy {
            populateTopTeams()
        }

    private fun populateTopTeams(): Map<String, LiveData<Result<List<Team>>>> {

    }

}