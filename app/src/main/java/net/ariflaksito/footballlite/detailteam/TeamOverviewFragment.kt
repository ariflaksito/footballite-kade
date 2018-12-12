package net.ariflaksito.footballlite.detailteam

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import net.ariflaksito.elitefootball.detail.TeamDetailPresenter
import net.ariflaksito.elitefootball.detail.TeamDetailView
import net.ariflaksito.footballlite.models.Team
import net.ariflaksito.footballlite.utils.ApiAccess
import net.ariflaksito.footballlite.utils.invisible
import net.ariflaksito.footballlite.utils.visible
import org.jetbrains.anko.*

class TeamOverviewFragment : Fragment(), AnkoComponent<Context>, TeamDetailView {
    private lateinit var progressBar: ProgressBar
    private lateinit var teamDescription: TextView
    private lateinit var presenter: TeamDetailPresenter

    override fun showTeamDetail(data: List<Team>) {
        teamDescription.text = data[0].teamDescription
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = createView(AnkoContext.create(requireContext()))
        var args = getArguments()
        var id = args?.getString("idTeam", "")

        val request = ApiAccess()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        id?.let { presenter.getTeamDetail(it) }
        return view
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            scrollView {
                isVerticalScrollBarEnabled = true
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        padding = dip(10)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL

                        teamDescription = textView().lparams {
                            topMargin = dip(10)
                            bottomMargin = dip(10)
                        }
                    }
                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }

        }

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}