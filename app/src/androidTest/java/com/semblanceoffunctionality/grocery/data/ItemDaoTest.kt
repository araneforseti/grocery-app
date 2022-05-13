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
    private val wantedItemA = Item("itemA", true)
    private val notWantedItemB = Item("itemB", false)
    private val wantedItemC = Item("itemC", true)

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
        assertThat(itemList[0].name, equalTo(wantedItemA.name))
        assertThat(itemList[1].name, equalTo(notWantedItemB.name))
        assertThat(itemList[2].name, equalTo(wantedItemC.name))
    }

    @Test
    fun testGetWantedItems() = runBlocking {
        val itemList = itemDao.getWantedItems().first()
        assertThat(itemList.size, equalTo(2))

        assertThat(itemList[0].name, equalTo(wantedItemA.name))
        assertThat(itemList[1].name, equalTo(wantedItemC.name))
    }

    @Test
    fun testGetItem() = runBlocking {
        val item = itemDao.getItem(notWantedItemB.name).first()

        assertThat(item.name, equalTo(notWantedItemB.name))
    }

    @Test
    fun testIsWanted_true() = runBlocking {
        assertThat(itemDao.isWanted(wantedItemA.name), equalTo(true))
    }

    @Test
    fun testIsWanted_false() = runBlocking {
        assertThat(itemDao.isWanted(notWantedItemB.name), equalTo(false))
    }

    @Test
    fun testSetWanted() = runBlocking {
        assertThat(itemDao.isWanted(notWantedItemB.name), equalTo(false))

        itemDao.setWanted(notWantedItemB.name)

        assertThat(itemDao.isWanted(notWantedItemB.name), equalTo(true))
    }

    @Test
    fun testSetNotWanted() = runBlocking {
        itemDao.setNotWanted(wantedItemA.name)

        assertThat(itemDao.isWanted(wantedItemA.name), equalTo(false))
    }

    @Test
    fun testDelete() = runBlocking {
        itemDao.deleteItem(wantedItemA.name)

        val itemList = itemDao.getItems().first()
        assertThat(itemList.size, equalTo(2))

        assertThat(itemList[0].name, equalTo(notWantedItemB.name))
        assertThat(itemList[1].name, equalTo(wantedItemC.name))
    }
}