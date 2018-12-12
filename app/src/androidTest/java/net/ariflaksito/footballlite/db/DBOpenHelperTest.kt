package net.ariflaksito.footballlite.db

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import net.ariflaksito.eplschedule.db.DBOpenHelper
import net.ariflaksito.eplschedule.db.FavoriteTeam
import net.ariflaksito.eplschedule.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DBOpenHelperTest {
    private lateinit var context: Context
    private lateinit var databaseHelper: DBOpenHelper

    @Before
    fun setup() {
        context = InstrumentationRegistry.getTargetContext()
        databaseHelper = DBOpenHelper(context)
        databaseHelper.truncate()
    }

    @After
    fun finish() {
        databaseHelper.truncate()
    }

    @Test
    fun testNotNull() {
        Assert.assertNotNull(databaseHelper)
    }

    @Test
    fun testAddFavorite() {
        val id = "1234"
        val team = "My Team"
        val badge = "http://ariflaksito.net/miniavatar.png"

        val res = addFavorite(id, team, badge)

        Assert.assertTrue(res)
    }

    @Test
    fun testEqualls(){
        val id = "1234"
        val team = "My Team"
        val badge = "http://ariflaksito.net/miniavatar.png"

        addFavorite(id, team, badge)
        val teams = getFavorite(id)
        val result = teams[0].teamId

        Assert.assertEquals(id, result)

    }

    @Test
    fun testEqualTeam(){
        val id = "1234"
        val team = "My Team"
        val badge = "http://ariflaksito.net/miniavatar.png"

        addFavorite(id, team, badge)
        val teams = getFavorite(id)
        val result = teams[0].teamName

        Assert.assertEquals(team, result)

    }

    @Test
    fun testEqualBadge(){
        val id = "1234"
        val team = "My Team"
        val badge = "http://ariflaksito.net/miniavatar.png"

        addFavorite(id, team, badge)
        val teams = getFavorite(id)
        val result = teams[0].teamBadge

        Assert.assertEquals(badge, result)

    }

    private fun addFavorite(id: String, team: String, badge: String): Boolean {
        try {
            context.database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE,
                    FavoriteTeam.TEAM_ID to id,
                    FavoriteTeam.TEAM_NAME to team,
                    FavoriteTeam.TEAM_BADGE to badge
                )
            }

            return true
        } catch (e: SQLiteConstraintException) {
            return false
        }
    }

    private fun delFavorite(id: String): Boolean {
        try {
            context.database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE, "(TEAM_ID = {id})",
                    "id" to id
                )
            }
            return true
        } catch (e: SQLiteConstraintException) {
            return false
        }

    }

    private fun getFavorite(id: String): MutableList<FavoriteTeam>{
        var favorites: MutableList<FavoriteTeam> = mutableListOf()
        context.database?.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
                .whereArgs("(TEAM_ID = {id})", "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
        }

        return favorites
    }


}