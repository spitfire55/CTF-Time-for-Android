package re.spitfy.ctftime.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.repo.TeamRepository

class TeamViewModel(private val teamRepo: TeamRepository) : ViewModel() {
    private lateinit var team: LiveData<Team>

    /*fun newModel(teamId: String) {
        team = teamRepo.getTeam(teamId)
    }*/

    fun getTeam(): LiveData<Team> {
        return team
    }

}