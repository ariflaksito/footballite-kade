package net.ariflaksito.footballlite.detailplayer

import net.ariflaksito.footballlite.models.Player

interface PlayerView{
    fun showLoading()
    fun hideLoading()
    fun showPlayer(data: Player)
}