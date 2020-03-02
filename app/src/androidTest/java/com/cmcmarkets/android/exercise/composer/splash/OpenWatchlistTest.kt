package com.cmcmarkets.android.exercise.composer.splash


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.cmcmarkets.android.exercise.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class OpenWatchlistTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun openWatchlistTest() {
        val materialButton = onView(
                allOf(withId(R.id.btnCreatedSession), withText("Create Session"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        materialButton.perform(click())

        val materialButton2 = onView(
                allOf(withId(R.id.btnSelectWatchlist), withText("Select Watchlist"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("androidx.core.widget.NestedScrollView")),
                                        0),
                                1),
                        isDisplayed()))
        materialButton2.perform(click())

        val watchlistsCard = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerViewWatchlists),
                                childAtPosition(
                                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                        1)),
                        1),
                        isDisplayed()))
        watchlistsCard.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
