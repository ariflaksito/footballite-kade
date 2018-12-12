package net.ariflaksito.footballlite.detailplayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.models.Player
import net.ariflaksito.footballlite.utils.ApiAccess
import org.jetbrains.anko.find

class PlayerActivity : AppCompatActivity(), PlayerView {

    private lateinit var fanArt: ImageView
    private lateinit var tvNation: TextView
    private lateinit var tvPos: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvDesc: TextView

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showPlayer(data: Player) {
        setTitle(data.strPlayer)
        Picasso.get().load(data.strFanart1).into(fanArt)

        tvNation.text = data.strNationality
        tvPos.text = data.strPosition
        tvHeight.text = data.strHeight
        tvDesc.text = data.strDescriptionEN
    }

    private lateinit var presenter: PlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fanArt = find(R.id.image_fanart)
        tvNation = find(R.id.tv_nation)
        tvPos = find(R.id.tv_pos)
        tvHeight = find(R.id.tv_height)
        tvDesc = find(R.id.tv_desc)

        val intent = intent
        val id = intent.getStringExtra("id")

        val api = ApiAccess()
        val gson = Gson()
        presenter = PlayerPresenter(this, api, gson)
        presenter.getPlayer(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
