package net.ariflaksito.footballlite.favorite

import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_match.view.*
import net.ariflaksito.eplschedule.db.FavoriteMatch
import net.ariflaksito.footballlite.R
import org.jetbrains.anko.find

class FavoriteMatchFragment : Fragment(), FavoriteMatchView {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var presenter: FavoriteMatchPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var matchAdapter: FavoriteMatchAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMatchList(data: MutableList<FavoriteMatch>) {
        swipeRefresh.isRefreshing = false
        favorites.clear()
        favorites.addAll(data)

        matchAdapter = FavoriteMatchAdapter(favorites)
        matchAdapter.notifyDataSetChanged()
        recyclerView.adapter = matchAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        setHasOptionsMenu(false)

        val rootView: View = inflater.inflate(R.layout.fragment_match, container, false)
        recyclerView = rootView.rv_match
        recyclerView.layoutManager = LinearLayoutManager(rootView.context)

        val spinner = rootView.find<Spinner>(R.id.spinner)
        spinner.visibility = View.GONE

        presenter = FavoriteMatchPresenter(context, this)
        presenter.getMatchList()

        swipeRefresh = SwipeRefreshLayout(rootView.context)
        swipeRefresh = rootView.swipeid

        swipeRefresh.setOnRefreshListener { presenter.getMatchList() }

        return rootView
    }

}