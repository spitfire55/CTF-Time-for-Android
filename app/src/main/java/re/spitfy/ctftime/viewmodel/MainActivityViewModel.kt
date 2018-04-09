package re.spitfy.ctftime.viewmodel

import android.arch.lifecycle.ViewModel
import re.spitfy.ctftime.data.QueryLiveData
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.repo.TeamRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        private val teamRepository: TeamRepository
): ViewModel(){

    val topTenTeams: MutableList<QueryLiveData<Team>> = queryTopTenTeams()

    fun queryTopTenTeams(): MutableList<QueryLiveData<Team>> {
        if (topTenTeams.isEmpty()) {
            for(year in 2011..2018) {
                teamRepository.topTenTeams(year.toString())?.apply {
                    topTenTeams.add(this)
                }
            }
        }
        return topTenTeams
    }
}