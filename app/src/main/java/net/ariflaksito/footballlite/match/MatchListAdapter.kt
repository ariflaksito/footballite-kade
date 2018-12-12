package net.ariflaksito.footballlite.match

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.CalendarContract
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.item_list_match.view.*
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.detailmatch.DetailMatchActivity
import net.ariflaksito.footballlite.models.Match
import net.ariflaksito.footballlite.utils.formatDate
import net.ariflaksito.footballlite.utils.formatDateTime
import net.ariflaksito.footballlite.utils.toMillis

class MatchListAdapter(private val matches: List<Match>, private var isSearch: Boolean) :
    RecyclerView.Adapter<MatchHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): MatchHolder {
        context = view.context
        return MatchHolder(
            LayoutInflater.from(view.context)
                .inflate(R.layout.item_list_match, view, false)
        )
    }

    override fun getItemCount(): Int = matches.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        holder.bindItem(matches[position], context, isSearch)
    }

}

class MatchHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val homeTeam: TextView = view.home_team
    private val awayTeam: TextView = view.away_team
    private val dateMatch: TextView = view.date_match
    private val homeScore: TextView = view.home_score
    private val awayScore: TextView = view.away_score
    private val eventId: LinearLayout = view.event_id
    private val imgAlarm: ImageView = view.img_alarm

    fun bindItem(match: Match, context: Context, search: Boolean) {
        homeTeam.text = match.homeTeam
        awayTeam.text = match.awayTeam
        if (!search) {
            dateMatch.text = formatDateTime(match.dateEvent + " " + match.timeEvent)
        }else {
            imgAlarm.visibility = View.GONE
            dateMatch.text = formatDate(match.dateEvent)
        }

        homeScore.text = match.homeScore
        awayScore.text = match.awayScore

        eventId.setOnClickListener {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra("idevent", match.eventId)
            intent.putExtra("idHome", match.idHomeTeam)
            intent.putExtra("idAway", match.idAwayTeam)
            intent.putExtra("dateevent", match.dateEvent)

            context.startActivity(intent)
        }

        imgAlarm.setOnClickListener{
            val matchTitle = "${match.homeTeam} VS ${match.awayTeam}"
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(CalendarContract.Events.TITLE, matchTitle)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, toMillis(match.dateEvent, match.timeEvent))
                .putExtra(CalendarContract.Events.ALL_DAY, false)

            context.startActivity(intent)
        }
    }

}