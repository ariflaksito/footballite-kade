package net.ariflaksito.elitefootball.detail

import net.ariflaksito.footballlite.models.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}