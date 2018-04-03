package re.spitfy.ctftime.viewmodel

import android.arch.lifecycle.ViewModel
import re.spitfy.ctftime.repo.TeamRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(teamRepository: TeamRepository) : ViewModel(){

    private val topTenTeams = teamRepository.topTenTeams()
}