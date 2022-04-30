package com.semblanceoffunctionality.grocery.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.semblanceoffunctionality.grocery.MainCoroutineRule
import com.semblanceoffunctionality.grocery.data.AppDatabase
import com.semblanceoffunctionality.grocery.data.ItemRepository
import com.semblanceoffunctionality.grocery.runBlockingTest
import com.semblanceoffunctionality.grocery.utilities.testItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class ItemDetailViewModelTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ItemDetailViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()
    private val itemId = "itemId"

    @get:Rule
    val rule = RuleChain
            .outerRule(hiltRule)
            .around(instantTaskExecutorRule)
            .around(coroutineRule)

    @Inject
    lateinit var itemRepository: ItemRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        val savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
            set(itemId, testItem.itemId)
        }
        viewModel = ItemDetailViewModel(savedStateHandle, itemRepository)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun testRemoveFromGrocery() = coroutineRule.runBlockingTest {
        itemRepository.createItem(itemId)

        viewModel.removeItemFromGrocery()

        assertThat(itemRepository.isWanted(itemId), equalTo(false))
    }
}
