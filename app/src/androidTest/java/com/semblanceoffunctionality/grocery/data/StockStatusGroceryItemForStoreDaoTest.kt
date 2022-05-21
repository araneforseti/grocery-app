package com.semblanceoffunctionality.grocery.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockStatusGroceryItemForStoreDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var itemDao: ItemDao
    private lateinit var storeDao: StoreDao
    private lateinit var stockStatusDao: StockStatusDao
    private lateinit var groceryDao: StockStatusGroceryItemForStoreDao
    private val wantedItemA = Item("itemA", true)
    private val notWantedItemB = Item("itemB", false)
    private val wantedItemC = Item("itemC", true)
    private val store = Store("store 1", "")
    private val store2 = Store("store 2", "")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        itemDao = database.itemDao()
        storeDao = database.storeDao()
        stockStatusDao = database.stockStatusDao()
        groceryDao = database.stockStatusGroceryItemForStoreDao()

        itemDao.insertAll(listOf(wantedItemA, notWantedItemB, wantedItemC))
        storeDao.insertAll(listOf(store, store2))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetItems() = runBlocking {
        val result = groceryDao.getWantedItemsAndStockStatus(store.name).first()
        val stockA = stockStatusDao.getStatus(store.name, wantedItemA.name)
        val stockC = stockStatusDao.getStatus(store.name, wantedItemC.name)

        ViewMatchers.assertThat(result.size, CoreMatchers.equalTo(2))
        ViewMatchers.assertThat(result[wantedItemA], CoreMatchers.equalTo(stockA))
        ViewMatchers.assertThat(result[wantedItemC], CoreMatchers.equalTo(stockC))
    }
}