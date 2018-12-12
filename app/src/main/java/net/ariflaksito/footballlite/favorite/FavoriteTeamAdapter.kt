package net.ariflaksito.footballlite.favorite

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.ariflaksito.eplschedule.db.FavoriteTeam
import net.ariflaksito.footballlite.R.id.player_foto
import net.ariflaksito.footballlite.R.id.player_name
import org.jetbrains.anko.*

class FavoriteTeamAdapter(private val favorite: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = player_foto
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = player_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(player_foto)
    private val teamName: TextView = view.find(player_name)

    fun bindItem(favorite: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        Picasso.get().load(favorite.teamBadge).into(teamBadge)
        teamName.text = favorite.teamName
        itemView.setOnClickListener { listener(favorite) }
    }
}