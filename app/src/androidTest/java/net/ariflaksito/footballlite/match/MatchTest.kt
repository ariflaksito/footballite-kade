package net.ariflaksito.footballlite.match

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import net.ariflaksito.footballlite.MainActivity
import android.support.test.rule.ActivityTestRule
import net.ariflaksito.footballlite.R.id.viewpager_main
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchTest{

    var activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    fun rule() = activityRule

    @Before
    fun setUp() {
        rule().activity.openFragment(CallMatchFragment())
    }

    @Test
    fun testDisplayTab() {
        // test display fragment utama
        Espresso.onView(ViewMatchers.withId(viewpager_main))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(viewpager_main))
            .perform(ViewActions.swipeLeft())
        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(viewpager_main))
            .perform(ViewActions.swipeUp())
        Thread.sleep(2000)

    }


}