package net.ariflaksito.elitefootball.detail

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.footballlite.match.CoroutineContextProvider
import net.ariflaksito.footballlite.models.TeamResponse
import net.ariflaksito.footballlite.utils.ApiAccess

class TeamDetailPresenter(
    private val view: TeamDetailView,
    private val api: ApiAccess,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var data: TeamResponse?
            data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.getTeam(teamId)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamDetail(data.teams)
        }
    }
}
