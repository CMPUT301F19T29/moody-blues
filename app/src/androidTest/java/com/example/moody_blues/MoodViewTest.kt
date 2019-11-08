package com.example.moody_blues

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasFlag
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.moody_blues.dashboard.DashboardView
import com.example.moody_blues.login.LoginView
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MoodViewTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(LoginView::class.java)

    @Test
    fun testSubmit() {
        // Click the button
        onView(withId(R.id.login_submit_button)).perform(click())
        // Ensure
        intended(hasFlag(Intent.FLAG_ACTIVITY_NEW_TASK))
        // Goes to dashboard
        intended(hasComponent(DashboardView::class.java.name))
    }

    @Test
    fun testSignUp() {
        // TODO: Write with SignUp
    }

}
