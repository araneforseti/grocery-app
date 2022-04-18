package com.semblanceoffunctionality.groceryapp.data

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.semblanceoffunctionality.groceryapp.utilities.DATABASE_NAME
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GroceryItemDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var groceryItemDao: GroceryItemDao
    private val wantedItem = GroceryItem("wanted1", true)
    private val unwantedItem = GroceryItem("unwanted2", false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
        Log.println(Log.DEBUG,"tag","Got here")

        groceryItemDao = database.groceryItemDao()
    }

    @After fun closeDb() {
        database.close()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase(DATABASE_NAME);
    }

    @Test
    fun testGetAllItems() = runBlocking {
        groceryItemDao.insertAll(listOf(wantedItem,unwantedItem))

        val resultList = groceryItemDao.getAll()
        assertThat(resultList.size, equalTo(2))
    }

    @Test
    fun testGetWantedItems_WhenOne() = runBlocking {
        groceryItemDao.insertAll(listOf(wantedItem,unwantedItem))

        val resultList = groceryItemDao.getAllWantedItems()

        assertThat(resultList.size, equalTo(1))
        assertThat(resultList[0], equalTo(wantedItem))
    }

    @Test
    fun testGetWantedItems_WhenNone() = runBlocking {
        groceryItemDao.insertAll(listOf(unwantedItem))

        val resultList = groceryItemDao.getAllWantedItems()

        assertThat(resultList.size, equalTo(0))
    }

    @Test
    fun testFindByName() = runBlocking {
        groceryItemDao.insertAll(listOf(wantedItem,unwantedItem))

        val resultList = groceryItemDao.findByName(unwantedItem.name)
        assertThat(resultList, equalTo(unwantedItem))
    }

    @Test
    fun testDeleteItem() = runBlocking {
        groceryItemDao.insertAll(listOf(wantedItem,unwantedItem))
        groceryItemDao.delete(wantedItem)

        val resultList = groceryItemDao.getAll()
        assertThat(resultList.size, equalTo(1))
        assertThat(resultList[0], equalTo(unwantedItem))
    }

    @Test
    fun testIsWanted_True() = runBlocking {
        groceryItemDao.insertAll(listOf(wantedItem, unwantedItem))
        val result = groceryItemDao.isWanted(wantedItem.name)
        assertThat(result[0], equalTo(wantedItem.wanted))
    }

    @Test
    fun testIsWanted_False() = runBlocking {
        groceryItemDao.insertAll(listOf(wantedItem, unwantedItem))

        val result = groceryItemDao.isWanted(unwantedItem.name)
        assertThat(result[0], equalTo(unwantedItem.wanted))
    }
}