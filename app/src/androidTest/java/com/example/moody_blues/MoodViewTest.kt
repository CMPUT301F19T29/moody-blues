package com.example.moody_blues

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.moody_blues.mood.MoodView
import com.example.moody_blues.mood.MoodView.Companion.INTENT_POS_RESULT
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MoodViewTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(MoodView::class.java)

    @Ignore
    @Test
    fun testConfirm() {
        // TODO: Refactor Mood before writing the rest of tests
        onView(withId(R.id.mood_save_button))
        intended(hasExtra(INTENT_POS_RESULT,1))
    }
}
