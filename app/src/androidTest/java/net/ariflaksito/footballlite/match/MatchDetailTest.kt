package net.ariflaksito.footballlite.match

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import net.ariflaksito.footballlite.MainActivity
import net.ariflaksito.footballlite.R
import net.ariflaksito.footballlite.R.id.add_to_favorite
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchDetailTest {

    var activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    fun rule() = activityRule

    @Before
    fun setUp() {
        rule().activity.openFragment(PrevMatchFragment())
    }

    @Test
    fun testDisplayMatch() {

        Espresso.onView(ViewMatchers.withId(R.id.rv_match))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(R.id.rv_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Thread.sleep(500)

        // lihat detail match
        Espresso.onView(ViewMatchers.withId(R.id.rv_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        Thread.sleep(1000)

        // test add to favorite
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)


    }
}