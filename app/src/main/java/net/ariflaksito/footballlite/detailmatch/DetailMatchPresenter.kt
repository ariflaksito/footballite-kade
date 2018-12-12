package net.ariflaksito.footballlite.detailmatch

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.footballlite.models.DetailMatchResponse
import net.ariflaksito.footballlite.models.TeamResponse
import net.ariflaksito.footballlite.utils.ApiAccess

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val api: ApiAccess,
    private val gson: Gson
) {
    fun getMatch(id: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.getDetailMatch(id)).await(),
                DetailMatchResponse::class.java
            )

            view.hideLoading()
            view.showMatchList(data.events[0])
        }
    }

    fun getTeam(id: String, isHome: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.getTeam(id)).await(),
                TeamResponse::class.java
            )

            if (isHome)
                view.showHomeTeam(data.teams[0])
            else view.showAwayTeam(data.teams[0])
        }
    }
}