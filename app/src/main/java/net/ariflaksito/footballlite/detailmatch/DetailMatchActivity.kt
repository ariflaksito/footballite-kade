package net.ariflaksito.footballlite.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import net.ariflaksito.eplschedule.db.FavoriteMatch
import net.ariflaksito.eplschedule.db.database
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.models.DetailMatch
import net.ariflaksito.footballlite.models.Team
import net.ariflaksito.footballlite.utils.ApiAccess
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import kotlinx.android.synthetic.main.activity_detail_match.*
import net.ariflaksito.footballlite.R.drawable.ic_add_to_favorites
import net.ariflaksito.footballlite.R.drawable.ic_added_to_favorites
import net.ariflaksito.footballlite.R.id.add_to_favorite


class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var match: DetailMatch

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var tvInfo: TextView

    private lateinit var tvHomeTeam: TextView
    private lateinit var tvAwayTeam: TextView
    private lateinit var tvHomeScore: TextView
    private lateinit var tvAwayScore: TextView
    private lateinit var imgHome: ImageView
    private lateinit var imgAway: ImageView

    private lateinit var tvHomeManager: TextView
    private lateinit var tvAwayManager: TextView
    private lateinit var tvHomeGoal: TextView
    private lateinit var tvAwayGoal: TextView
    private lateinit var tvHomeYellow: TextView
    private lateinit var tvAwayYellow: TextView
    private lateinit var tvHomeRed: TextView
    private lateinit var tvAwayRed: TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var idMatch: String
    private lateinit var idHome: String
    private lateinit var idAway: String
    private lateinit var dateEvent: String
    private lateinit var homeTeam: Team
    private lateinit var awayTeam: Team
    private var homeScore: String = ""
    private var awayScore: String = ""


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMatchList(data: DetailMatch) {
        match = data

        menuItem?.getItem(0)?.setVisible(true)

        setTitle(match.homeTeam + " vs " + match.awayTeam)
        favoriteState()
        setFavorite()

        tvInfo.text = match.infoMatch
        tvHomeTeam.text = match.homeTeam
        tvAwayTeam.text = match.awayTeam
        tvHomeScore.text = match.homeScore
        tvAwayScore.text = match.awayScore

        try {
            homeScore = match.homeScore!!
            awayScore = match.awayScore!!
            tvHomeGoal.text = Html.fromHtml(match.homeGoal?.replace(";", "<br />"))
            tvAwayGoal.text = Html.fromHtml(match.awayGoal?.replace(";", "<br />"))
            tvHomeYellow.text = Html.fromHtml(match.homeYellowCard?.replace(";", "<br />"))
            tvAwayYellow.text = Html.fromHtml(match.awayYellowCard?.replace(";", "<br />"))
            tvHomeRed.text = Html.fromHtml(match.homeRedCard?.replace(";", "<br />"))
            tvAwayRed.text = Html.fromHtml(match.awayRedCard?.replace(";", "<br />"))
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun showHomeTeam(data: Team) {
        homeTeam = data
        Picasso.get().load(data.teamBadge).into(imgHome)
        tvHomeManager.text = data.teamManager
    }

    override fun showAwayTeam(data: Team) {
        awayTeam = data
        Picasso.get().load(data.teamBadge).into(imgAway)
        tvAwayManager.text = data.teamManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_detail_match)

        idMatch = intent.getStringExtra("idevent")
        dateEvent = intent.getStringExtra("dateevent")
        idHome = intent.getStringExtra("idHome")
        idAway = intent.getStringExtra("idAway")

        tvInfo = find(R.id.match_info)
        tvHomeTeam = find(R.id.home_team)
        tvAwayTeam = find(R.id.away_team)
        tvHomeScore = find(R.id.home_score)
        tvAwayScore = find(R.id.away_score)

        imgHome = find(R.id.imgHome)
        imgAway = find(R.id.imgAway)
        tvHomeManager = find(R.id.home_manager)
        tvAwayManager = find(R.id.away_manager)

        tvHomeGoal = find(R.id.home_goal)
        tvAwayGoal = find(R.id.away_goal)
        tvHomeYellow = find(R.id.home_yellowc)
        tvAwayYellow = find(R.id.away_yellowc)
        tvHomeRed = find(R.id.home_redc)
        tvAwayRed = find(R.id.away_redc)



        val request = ApiAccess()
        val gson = Gson()
        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getMatch(idMatch)
        presenter.getTeam(idHome, true)
        presenter.getTeam(idAway, false)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        menuItem?.getItem(0)?.setVisible(false)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteMatch.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to idMatch
                )
            }
            Snackbar.make(layout, "Removed to favorite", Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE,
                    FavoriteMatch.EVENT_ID to idMatch,
                    FavoriteMatch.EVENT_DATE to dateEvent,
                    FavoriteMatch.EVENT_HOME_TEAM to homeTeam.teamName,
                    FavoriteMatch.EVENT_AWAY_TEAM to awayTeam.teamName,
                    FavoriteMatch.EVENT_HOME_SCORE to homeScore,
                    FavoriteMatch.EVENT_AWAY_SCORE to awayScore,
                    FavoriteMatch.EVENT_HOME_BADGE to homeTeam.teamBadge,
                    FavoriteMatch.EVENT_AWAY_BADGE to awayTeam.teamBadge,
                    FavoriteMatch.EVENT_HOME_ID to idHome,
                    FavoriteMatch.EVENT_AWAY_ID to idAway
                )
            }
            Snackbar.make(layout, "Added to favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun favoriteState() {
        try {
            database.use {
                val result = select(FavoriteMatch.TABLE_FAVORITE)
                    .whereArgs(
                        "(EVENT_ID = {id})",
                        "id" to idMatch
                    )
                val favorite = result.parseList(classParser<FavoriteMatch>())
                if (!favorite.isEmpty()) isFavorite = true
            }
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }
}
