package net.ariflaksito.footballlite.match

import net.ariflaksito.footballlite.models.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun noFound(msg: String)
    fun showMatchList(data: List<Match>)
    fun showSearchList(data: List<Match>)
}