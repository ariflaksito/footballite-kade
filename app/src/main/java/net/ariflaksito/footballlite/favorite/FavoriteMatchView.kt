package net.ariflaksito.footballlite.favorite

import net.ariflaksito.eplschedule.db.FavoriteMatch
import net.ariflaksito.footballlite.models.Match

interface FavoriteMatchView{
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: MutableList<FavoriteMatch>)
}