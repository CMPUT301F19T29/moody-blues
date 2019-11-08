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
import com.example.moody_blues.signup.SignupView
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginViewTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(LoginView::class.java)

    @Ignore
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
        onView(withId(R.id.login_signup_button)).perform(click())
        intended(hasComponent(SignupView::class.java.name))
    }

}
