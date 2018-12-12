package net.ariflaksito.footballlite.match

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.footballlite.models.MatchResponse
import net.ariflaksito.footballlite.models.SearchMatchResponse
import net.ariflaksito.footballlite.utils.ApiAccess
import kotlin.coroutines.CoroutineContext

class MatchPresenter(
    private val view: MatchView,
    private val api: ApiAccess,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getMatchList(id: String, isPrev: Boolean) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {

            var data: MatchResponse?
            if (isPrev) {
                data = gson.fromJson(
                    api
                        .doRequest(ApiAccess.ApiMatch.getPrevMatch(id)).await(),
                    MatchResponse::class.java
                )
            } else {
                data = gson.fromJson(
                    api
                        .doRequest(ApiAccess.ApiMatch.getNextMatch(id)).await(),
                    MatchResponse::class.java
                )
            }

            view.hideLoading()
            if (data.events == null)
                view.noFound("")
            else
                view.showMatchList(data.events)
        }
    }

    fun searchMatchList(keyword: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {

            var data = gson.fromJson(
                api
                    .doRequest(ApiAccess.ApiMatch.searchMatch(keyword)).await(),
                SearchMatchResponse::class.java
            )

            view.hideLoading()
            if (data.event == null)
                view.noFound("")
            else
                view.showSearchList(data.event)
        }
    }

}

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
}