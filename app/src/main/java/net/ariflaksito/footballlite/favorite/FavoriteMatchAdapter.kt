package net.ariflaksito.footballlite.favorite

import android.content.Context
import android.content.Intent
import android.os.Build
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
import net.ariflaksito.eplschedule.db.FavoriteMatch
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.detailmatch.DetailMatchActivity
import net.ariflaksito.footballlite.utils.formatDate

class FavoriteMatchAdapter(private val matches: List<FavoriteMatch>) :
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
        holder.bindItem(matches[position], context)
    }

}

class MatchHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val homeTeam: TextView = view.home_team
    private val awayTeam: TextView = view.away_team
    private val dateMatch: TextView = view.date_match
    private val homeScore: TextView = view.home_score
    private val awayScore: TextView = view.away_score
    private val eventId: LinearLayout = view.event_id
    private val imageAlarm: ImageView = view.img_alarm


    fun bindItem(match: FavoriteMatch, context: Context) {
        homeTeam.text = match.homeTeam
        awayTeam.text = match.awayTeam
        dateMatch.text = formatDate(match.eventDate)
        homeScore.text = match.homeScore
        awayScore.text = match.awayScore
        imageAlarm.visibility = View.GONE

        eventId.setOnClickListener {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra("idevent", match.eventId)
            intent.putExtra("idHome", match.homeId)
            intent.putExtra("idAway", match.awayId)
            intent.putExtra("dateevent", match.eventDate)

            context.startActivity(intent)
        }
    }

}