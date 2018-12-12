package net.ariflaksito.footballlite.detailteam

import android.database.sqlite.SQLiteConstraintException
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import net.ariflaksito.footballlite.R
import kotlinx.android.synthetic.main.activity_team.*
import net.ariflaksito.elitefootball.detail.TeamDetailPresenter
import net.ariflaksito.elitefootball.detail.TeamDetailView
import net.ariflaksito.eplschedule.db.FavoriteTeam
import net.ariflaksito.eplschedule.db.database
import net.ariflaksito.footballlite.models.Team
import net.ariflaksito.footballlite.utils.ApiAccess
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find

class TeamActivity : AppCompatActivity(), TeamDetailView {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team

    private var desc: String? = ""

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showTeamDetail(data: List<Team>) {

        menuItem?.getItem(0)?.setVisible(true)

        teams = Team(
            data[0].teamId,
            data[0].teamName,
            data[0].teamBadge
        )

        favoriteState()
        setFavorite()

        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamFormedYear.text = data[0].teamFormedYear
        desc = data[0].teamDescription

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        teamBadge = find(R.id.image_team)
        teamName = find(R.id.tv_team)
        teamFormedYear = find(R.id.tv_year)

        val intent = intent
        id = intent.getStringExtra("id")
        favoriteState()
        setFavorite()

        val request = ApiAccess()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)



        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        menuItem?.getItem(0)?.setVisible(false)

        return true
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            var args = Bundle()
            args.putString("idTeam", id)


            return when (position) {
                0 -> {
                    var fragment = TeamOverviewFragment()
                    fragment.arguments = args
                    fragment
                }
                else -> {
                    var fragment = TeamPlayersFragment()
                    fragment.arguments = args
                    fragment
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE,
                    FavoriteTeam.TEAM_ID to teams.teamId,
                    FavoriteTeam.TEAM_NAME to teams.teamName,
                    FavoriteTeam.TEAM_BADGE to teams.teamBadge
                )
            }
            Snackbar.make(main_content, "Added to favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(main_content, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE, "(TEAM_ID = {id})",
                    "id" to id
                )
            }
            Snackbar.make(main_content, "Removed to favorite", Snackbar.LENGTH_SHORT).show()

        } catch (e: SQLiteConstraintException) {
            Snackbar.make(main_content, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}
