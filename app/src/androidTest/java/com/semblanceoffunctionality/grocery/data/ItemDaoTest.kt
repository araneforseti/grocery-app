package com.semblanceoffunctionality.grocery.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var itemDao: ItemDao
    private val wantedItemA = Item("itemA", "itemA", true)
    private val notWantedItemB = Item("itemB", "itemB", false)
    private val wantedItemC = Item("itemC", "itemC", true)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        itemDao = database.itemDao()
        itemDao.insertAll(listOf(wantedItemA, notWantedItemB, wantedItemC))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetItems() = runBlocking {
        val itemList = itemDao.getItems().first()
        assertThat(itemList.size, equalTo(3))
        assertThat(itemList[0], equalTo(wantedItemA))
        assertThat(itemList[1], equalTo(notWantedItemB))
        assertThat(itemList[2], equalTo(wantedItemC))
    }

    @Test
    fun testGetWantedItems() = runBlocking {
        val itemList = itemDao.getWantedItems().first()
        assertThat(itemList.size, equalTo(2))

        assertThat(itemList[0], equalTo(wantedItemA))
        assertThat(itemList[1], equalTo(wantedItemC))
    }

    @Test
    fun testGetItem() = runBlocking {
        val item = itemDao.getItem(notWantedItemB.itemId).first()

        assertThat(item, equalTo(notWantedItemB))
    }

    @Test
    fun testIsWanted_true() = runBlocking {
        assertThat(itemDao.isWanted(wantedItemA.itemId), equalTo(true))
    }

    @Test
    fun testIsWanted_false() = runBlocking {
        assertThat(itemDao.isWanted(notWantedItemB.itemId), equalTo(false))
    }

    @Test
    fun testSetWanted() = runBlocking {
        itemDao.setWanted(notWantedItemB.itemId)

        assertThat(itemDao.isWanted(notWantedItemB.itemId), equalTo(true))
    }

    @Test
    fun testSetNotWanted() = runBlocking {
        itemDao.setNotWanted(wantedItemA.itemId)

        assertThat(itemDao.isWanted(wantedItemA.itemId), equalTo(false))
    }

    @Test
    fun testDelete() = runBlocking {
        itemDao.deleteItem(wantedItemA.itemId)

        val itemList = itemDao.getItems().first()
        assertThat(itemList.size, equalTo(2))

        assertThat(itemList[0], equalTo(notWantedItemB))
        assertThat(itemList[2], equalTo(wantedItemC))
    }
}