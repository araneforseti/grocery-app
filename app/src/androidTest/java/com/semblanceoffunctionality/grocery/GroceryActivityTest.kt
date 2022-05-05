package com.semblanceoffunctionality.grocery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class GroceryActivityTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(GroceryActivity::class.java)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test fun clickAddItem_OpensItemList() {
        // Given that no Items are added to the user's grocery

        // When the "Add Item" button is clicked
        onView(withId(R.id.add_item)).perform(click())

        // Then the ViewPager should change to the Item List page
        onView(withId(R.id.item_list)).check(matches(isDisplayed()))
    }
}
