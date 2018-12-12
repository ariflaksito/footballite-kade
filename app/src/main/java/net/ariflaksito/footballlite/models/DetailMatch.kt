package net.ariflaksito.footballlite.models

import com.google.gson.annotations.SerializedName

data class DetailMatch (
    @SerializedName("strFilename")
    var infoMatch: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strTime")
    var timeEvent: String? = null,

    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoal: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoal: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRedCard: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRedCard: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellowCard: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellowCard: String? = null

)