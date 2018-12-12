package net.ariflaksito.eplschedule.db

data class FavoriteMatch(val id: Long?, val eventId: String?, val eventDate: String?, val homeTeam: String?,
                    val awayTeam: String?, val homeScore: String?, val awayScore: String?,
                    val homeBadge: String?, val awayBadge: String?, val homeId: String?, val awayId: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TB_FAV_MATCH"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_HOME_TEAM: String = "EVENT_HOME_TEAM"
        const val EVENT_AWAY_TEAM: String = "EVENT_AWAY_TEAM"
        const val EVENT_HOME_SCORE: String = "EVENT_HOME_SCORE"
        const val EVENT_AWAY_SCORE: String = "EVENT_AWAY_SCORE"
        const val EVENT_HOME_BADGE: String = "EVENT_HOME_BADGE"
        const val EVENT_AWAY_BADGE: String = "EVENT_AWAY_BADGE"
        const val EVENT_HOME_ID: String = "EVENT_HOME_ID"
        const val EVENT_AWAY_ID: String = "EVENT_AWAY_ID"
    }
}

data class FavoriteTeam(val id: Long?, val teamId: String?, val teamName: String?, val teamBadge: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TB_FAV_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}