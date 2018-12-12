package net.ariflaksito.elitefootball.teams

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.footballlite.match.CoroutineContextProvider
import net.ariflaksito.footballlite.models.TeamResponse
import net.ariflaksito.footballlite.utils.ApiAccess

class TeamsPresenter(
    private val view: TeamsView,
    private val api: ApiAccess,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getTeamList(league: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var data: TeamResponse?
            data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.getTeams(league)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamList(data.teams)
        }
    }

    fun searchTeamList(league: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var data: TeamResponse?
            data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.searchTeam(league)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            if(data.teams == null){
                view.noFound("Result not found")
            }else
                view.showTeamList(data.teams)

        }
    }

    fun hideSpinnerView(){
        view.hideSpinner()
    }

    fun loadSpinnerView(){
        view.showSpinner()
    }
    
    
}