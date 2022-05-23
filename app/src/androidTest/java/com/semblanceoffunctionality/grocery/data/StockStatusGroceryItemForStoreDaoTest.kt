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
    private val stockStatusA1 = StockStatus(wantedItemA.name, store.name, StockStatusEnum.STOCKED)
    private val stockStatusA2 = StockStatus(wantedItemA.name, store2.name, StockStatusEnum.UNKNOWN)
    private val stockStatusB1 = StockStatus(notWantedItemB.name, store.name, StockStatusEnum.NOT_STOCKED)
    private val stockStatusB2 = StockStatus(notWantedItemB.name, store2.name, StockStatusEnum.UNKNOWN)
    private val stockStatusC1 = StockStatus(wantedItemC.name, store.name, StockStatusEnum.UNKNOWN)
    private val stockStatusC2 = StockStatus(wantedItemC.name, store2.name, StockStatusEnum.NOT_STOCKED)

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
        stockStatusDao.insertAll(listOf(stockStatusA1,stockStatusA2,
            stockStatusB1,stockStatusB2,
            stockStatusC1,stockStatusC2))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetItems() = runBlocking {
        val result = groceryDao.getWantedItemsAndStockStatus(store.name).first()

        ViewMatchers.assertThat(result.size, CoreMatchers.equalTo(2))
        ViewMatchers.assertThat(result[wantedItemA], CoreMatchers.equalTo(stockStatusA1))
        ViewMatchers.assertThat(result[wantedItemC], CoreMatchers.equalTo(stockStatusC1))
    }
}