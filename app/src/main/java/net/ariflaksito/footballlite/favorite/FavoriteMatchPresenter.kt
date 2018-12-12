package net.ariflaksito.footballlite.favorite

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ariflaksito.eplschedule.db.FavoriteMatch
import net.ariflaksito.eplschedule.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter(
    private val context: Context?,
    private val view: FavoriteMatchView

) {

    fun getMatchList() {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var favorites: MutableList<FavoriteMatch> = mutableListOf()
            context?.database?.use {
                val result = select(FavoriteMatch.TABLE_FAVORITE)
                val favorite = result.parseList(classParser<FavoriteMatch>())
                favorites.addAll(favorite)
            }

            view.showMatchList(favorites)
            view.hideLoading()
        }
    }
}