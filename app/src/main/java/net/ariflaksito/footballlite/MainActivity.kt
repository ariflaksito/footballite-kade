package net.ariflaksito.footballlite

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.ariflaksito.elitefootball.teams.TeamsFragment
import net.ariflaksito.footballlite.favorite.CallFavoriteFragment
import net.ariflaksito.footballlite.match.CallMatchFragment
import net.ariflaksito.footballlite.utils.ApiAccess
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_match -> {
                val fragment = CallMatchFragment()
                fragment.setHasOptionsMenu(true)
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                val fragment = TeamsFragment()
                fragment.setHasOptionsMenu(true)
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                val fragment = CallFavoriteFragment()
                fragment.setHasOptionsMenu(false)
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun openFragment(fragment: Fragment) {

        val api = ApiAccess()
        if(api.isNetworkAvailable(this)){

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.elevation = 0F

        val fragment = CallMatchFragment()
        fragment.setHasOptionsMenu(true)
        openFragment(fragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
