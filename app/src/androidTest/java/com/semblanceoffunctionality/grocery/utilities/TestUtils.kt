package com.semblanceoffunctionality.grocery.utilities

import android.content.Intent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.semblanceoffunctionality.grocery.data.Item
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf

/**
 * [Item] objects used for tests.
 */
val testItems = arrayListOf(
    Item("1", "Apple", true),
    Item("2", "Banana", false),
    Item("3", "Coconut", true)
)
val testItem = testItems[0]

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
)
