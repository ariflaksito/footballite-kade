package net.ariflaksito.footballlite.detailplayer

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.footballlite.match.CoroutineContextProvider
import net.ariflaksito.footballlite.models.DetailPlayerResponse
import net.ariflaksito.footballlite.models.PlayerResponse
import net.ariflaksito.footballlite.utils.ApiAccess

class PlayerPresenter(
    private val view: PlayerView,
    private val api: ApiAccess,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getPlayer(id: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var data: DetailPlayerResponse?
            data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.getPlayer(id)).await(),
                DetailPlayerResponse::class.java
            )

            view.hideLoading()
            view.showPlayer(data.players.get(0))
        }
    }
}