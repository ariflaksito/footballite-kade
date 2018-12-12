package net.ariflaksito.eplschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 3) {
    companion object {
        private var instance: DBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBOpenHelper {
            if (instance == null) {
                instance = DBOpenHelper(ctx.applicationContext)
            }
            return instance as DBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteMatch.TABLE_FAVORITE, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
            FavoriteMatch.EVENT_DATE to TEXT,
            FavoriteMatch.EVENT_HOME_TEAM to TEXT,
            FavoriteMatch.EVENT_AWAY_TEAM to TEXT,
            FavoriteMatch.EVENT_HOME_SCORE to TEXT,
            FavoriteMatch.EVENT_AWAY_SCORE to TEXT,
            FavoriteMatch.EVENT_HOME_BADGE to TEXT,
            FavoriteMatch.EVENT_AWAY_BADGE to TEXT,
            FavoriteMatch.EVENT_HOME_ID to TEXT,
            FavoriteMatch.EVENT_AWAY_ID to TEXT
            )

        db.createTable(FavoriteTeam.TABLE_FAVORITE, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE, true)
        onCreate(db)
    }

    fun truncate() {
        writableDatabase.execSQL("DELETE FROM ${FavoriteTeam.TABLE_FAVORITE}")
        writableDatabase.execSQL("DELETE FROM ${FavoriteMatch.TABLE_FAVORITE}")
    }
}

// Access property for Context
val Context.database: DBOpenHelper
    get() = DBOpenHelper.getInstance(applicationContext)