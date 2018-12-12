package net.ariflaksito.footballlite.detailteam

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.models.Player
import org.jetbrains.anko.*

class TeamPlayersAdapter(private val players: List<Player>, private val listener: (Player) -> Unit) :
    RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            TeamUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

}

class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val playerFoto: ImageView = view.find(R.id.player_foto)
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerPos: TextView = view.find(R.id.player_pos)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        Picasso.get().load(player.strCutout).into(playerFoto)
        playerName.text = player.strPlayer
        playerPos.text = player.strPosition
        itemView.setOnClickListener { listener(player) }
    }
}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.player_foto
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                linearLayout{
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL

                    textView {
                        id = R.id.player_name
                        textSize = 16f
                    }.lparams {
                        topMargin = dip(10)
                        leftMargin = dip(15)
                    }

                    textView {
                        id = R.id.player_pos
                        textSize = 12f
                        textColor = ContextCompat.getColor(context, R.color.colorAccent)
                    }.lparams {
                        leftMargin = dip(15)
                    }
                }

            }
        }
    }
}