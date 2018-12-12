package net.ariflaksito.footballlite.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.ariflaksito.footballlite.BuildConfig
import net.ariflaksito.footballlite.MainActivity
import java.net.URL

class ApiAccess {

    private lateinit var cn: Context

    fun ApiAccess(context: Context) {
        cn = context
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun doRequest(url: String): Deferred<String> = GlobalScope.async {
            URL(url).readText()
    }

    object ApiMatch {
        fun getPrevMatch(id: String?): String {
            val path: String = "eventspastleague.php"
            return connectApi(path, id)
        }

        fun getNextMatch(id: String?): String {
            val path: String = "eventsnextleague.php"
            return connectApi(path, id)
        }

        fun getDetailMatch(id: String?): String {
            val path: String = "lookupevent.php"
            return connectApi(path, id)
        }

        fun getTeam(id: String?): String {
            val path: String = "lookupteam.php"
            return connectApi(path, id)
        }

        fun getPlayers(id: String?): String {
            val path: String = "lookup_all_players.php"
            return connectApi(path, id)
        }

        fun getPlayer(id: String?): String {
            val path: String = "lookupplayer.php"
            return connectApi(path, id)
        }

        fun getTeams(id: String?): String {
            val path: String = "search_all_teams.php"
            return connectApi(path, id, "l")
        }

        fun searchTeam(id: String?): String {
            val path: String = "searchteams.php"
            return connectApi(path, id, "t")
        }

        fun searchMatch(id: String?): String {
            val path: String = "searchevents.php"
            return connectApi(path, id, "e")
        }

        fun connectApi(path: String, id: String?, pid: String? = "id"): String {
            return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath(path)
                .appendQueryParameter(pid, id)
                .build()
                .toString()
        }


    }
}