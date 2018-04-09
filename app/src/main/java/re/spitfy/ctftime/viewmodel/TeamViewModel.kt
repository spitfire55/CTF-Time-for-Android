package re.spitfy.ctftime.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.repo.TeamRepository
import javax.inject.Inject


class TeamViewModel @Inject constructor(teamRepository: TeamRepository) : ViewModel() {
    private var teamId: MutableLiveData<String> = MutableLiveData()
    private val team: LiveData<Resource<Team>> = Transformations.switchMap(teamId, teamRepository::team)


}