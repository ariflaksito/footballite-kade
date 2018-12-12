package net.ariflaksito.footballlite.models

data class MatchResponse(
    val events: List<Match>
)

data class SearchMatchResponse(
    val event: List<Match>
)

data class DetailMatchResponse(
    val events: List<DetailMatch>
)

data class TeamResponse(
    val teams: List<Team>
)

data class PlayerResponse(
    val player: List<Player>
)

data class DetailPlayerResponse(
    val players: List<Player>
)
