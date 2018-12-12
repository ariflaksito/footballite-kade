package net.ariflaksito.footballlite.detailteam

import net.ariflaksito.footballlite.models.Player

interface TeamPlayersView{
    fun showLoading()
    fun hideLoading()
    fun showPlayers(data: List<Player>)
}