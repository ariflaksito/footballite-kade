package net.ariflaksito.footballlite.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match.view.*
import net.ariflaksito.footballlite.BuildConfig

import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.models.Match
import net.ariflaksito.footballlite.utils.ApiAccess
import org.jetbrains.anko.find

class NextMatchFragment : Fragment(), MatchView {



    private var matches: MutableList<Match> = mutableListOf()

    private lateinit var presenter: MatchPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var matchAdapter: MatchListAdapter

    override fun showLoading() { }

    override fun hideLoading() { }

    override fun noFound(msg: String) {

    }

    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)

        matchAdapter = MatchListAdapter(matches, false)
        matchAdapter.notifyDataSetChanged()
        recyclerView.adapter = matchAdapter
    }

    override fun showSearchList(data: List<Match>) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_match, container, false)
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        val spinner = view.find<Spinner>(R.id.spinner)
        spinner.adapter = spinnerAdapter

        recyclerView = view.rv_match
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val gson = Gson()
        val api = ApiAccess()
        presenter = MatchPresenter(this, api, gson)
        presenter.getMatchList(BuildConfig.LEAGUE, false)

        swipeRefresh = SwipeRefreshLayout(view.context)
        swipeRefresh = view.swipeid
        swipeRefresh.isRefreshing = true

        swipeRefresh.setOnRefreshListener { presenter.getMatchList(BuildConfig.LEAGUE, false) }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val idx = arrayOf<String>("4328","4331","4332","4334","4335")
                swipeRefresh.isRefreshing = true
                presenter.getMatchList(idx[position], false)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        return view
    }


}
