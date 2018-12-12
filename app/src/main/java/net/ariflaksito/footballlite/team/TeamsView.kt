package net.ariflaksito.elitefootball.teams

import net.ariflaksito.footballlite.models.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun hideSpinner()
    fun showSpinner()
    fun noFound(msg: String)
    fun showTeamList(data: List<Team>)
}