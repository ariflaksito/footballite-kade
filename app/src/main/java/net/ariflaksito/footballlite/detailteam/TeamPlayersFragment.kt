package net.ariflaksito.footballlite.detailteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.detailplayer.PlayerActivity
import net.ariflaksito.footballlite.models.Player
import net.ariflaksito.footballlite.utils.ApiAccess
import net.ariflaksito.footballlite.utils.invisible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamPlayersFragment : Fragment(), AnkoComponent<Context>, TeamPlayersView {
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: TeamPlayersPresenter
    private lateinit var adapter: TeamPlayersAdapter
    private lateinit var listPlayer: RecyclerView

    override fun showLoading() {

    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayers(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = createView(AnkoContext.create(requireContext()))
        var args = getArguments()
        var id = args?.getString("idTeam", "")

        val api = ApiAccess()
        val gson = Gson()
        presenter = TeamPlayersPresenter(this, api, gson)
        id?.let { presenter.getListPlayers(it) }

        adapter = TeamPlayersAdapter(players){
            context?.startActivity<PlayerActivity>("id" to "${it.idPlayer}")
        }
        listPlayer.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            id?.let { presenter.getListPlayers(it) }
        }

        return  view
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

}