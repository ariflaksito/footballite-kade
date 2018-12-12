package net.ariflaksito.footballlite.detailmatch

import net.ariflaksito.footballlite.models.DetailMatch
import net.ariflaksito.footballlite.models.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: DetailMatch)
    fun showHomeTeam(data: Team)
    fun showAwayTeam(data: Team)
}