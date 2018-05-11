package re.spitfy.ctftime.presentation.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.presentation.Result
import re.spitfy.ctftime.data.repo.TeamRepository
import re.spitfy.ctftime.utils.SchedulerProvider
import re.spitfy.ctftime.utils.extensions.toLiveData
import re.spitfy.ctftime.utils.extensions.toResult
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val teamRepository: TeamRepository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel()