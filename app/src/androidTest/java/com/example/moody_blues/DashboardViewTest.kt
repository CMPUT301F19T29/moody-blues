package com.example.moody_blues

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.moody_blues.dashboard.DashboardView
import com.example.moody_blues.history.HistoryView
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DashboardViewTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(DashboardView::class.java)

    @Test
    fun testHistory() {

        // Click the button
        onView(withId(R.id.dashboard_history_button)).perform(click())
        // Goes to dashboard
        intended(hasComponent(HistoryView::class.java.name))
    }
}
