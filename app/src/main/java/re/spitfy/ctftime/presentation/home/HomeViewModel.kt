package re.spitfy.ctftime.presentation.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.presentation.Result
import re.spitfy.ctftime.repo.TeamRepository
import re.spitfy.ctftime.utils.SchedulerProvider
import re.spitfy.ctftime.utils.extensions.toResult
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        // Not worried about multiple repos being passed as parameters because they are
        // annotated as Singletons during Dagger2 injection and it just makes more sense
        private val teamRepository: TeamRepository,
        private val eventRepository: EventRepository,
        private val writeupRepository: WriteupRepository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val topTeams: LiveData<Result<Map<String, List<Team>>>> by lazy {
        teamRepository.topTenTeams.toResult(schedulerProvider).toLiveData()

}