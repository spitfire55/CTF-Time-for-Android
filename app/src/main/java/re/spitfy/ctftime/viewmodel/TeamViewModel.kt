package re.spitfy.ctftime.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.repo.FirestoreRepository

class TeamViewModel(private val teamRepo: FirestoreRepository) : ViewModel() {
    private lateinit var team: LiveData<Team>

    /*fun newModel(teamId: String) {
        team = teamRepo.getTeam(teamId)
    }*/

    fun getTeam(): LiveData<Team> {
        return team
    }

}