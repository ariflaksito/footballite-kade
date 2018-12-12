package net.ariflaksito.footballlite.detailteam

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.footballlite.match.CoroutineContextProvider
import net.ariflaksito.footballlite.models.PlayerResponse
import net.ariflaksito.footballlite.utils.ApiAccess

class TeamPlayersPresenter(
    private val view: TeamPlayersView,
    private val api: ApiAccess,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getListPlayers(teamId: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var data: PlayerResponse?
            data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.getPlayers(teamId)).await(),
                PlayerResponse::class.java
            )

            view.hideLoading()
            if (data.player != null)
                view.showPlayers(data.player)
        }
    }
}