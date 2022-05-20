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
class StockStatusRepositoryTest {
    private lateinit var database: AppDatabase
    private lateinit var itemDao: ItemDao
    private lateinit var storeDao: StoreDao
    private lateinit var stockStatusDao: StockStatusDao
    private lateinit var stockStatusRepository: StockStatusRepository

    private val itemA = Item("itemA")
    private val itemB = Item("itemB")
    private val itemC = Item("itemC")

    private val store1 = Store("store1", "")
    private val store2 = Store("store2", "")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        itemDao = database.itemDao()
        storeDao = database.storeDao()
        stockStatusDao = database.stockStatusDao()
        stockStatusRepository = StockStatusRepository(stockStatusDao, itemDao, storeDao)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testStockForStoresAdded_WhenItemsExist_ShouldSetToUnknown() = runBlocking {
        itemDao.insertAll(listOf(itemA, itemB, itemC))
        storeDao.insertAll(listOf(store1))

        stockStatusRepository.addStockStatusesForStore(store1.name)

        val result = stockStatusRepository.getAllStockStatusesForStore(store1.name).first()
        assertThat(result.size, equalTo(3))
        assertThat(result[0].item, equalTo(itemA.name))
        assertThat(result[0].store, equalTo(store1.name))
        assertThat(result[0].stockStatus, equalTo(StockStatusEnum.UNKNOWN))
        assertThat(result[1].item, equalTo(itemB.name))
        assertThat(result[1].stockStatus, equalTo(StockStatusEnum.UNKNOWN))
        assertThat(result[1].store, equalTo(store1.name))
        assertThat(result[2].item, equalTo(itemC.name))
        assertThat(result[2].stockStatus, equalTo(StockStatusEnum.UNKNOWN))
        assertThat(result[2].store, equalTo(store1.name))
    }

    @Test
    fun testStockForItemsAdded_WhenStoresExist_ShouldSetToUnknown() = runBlocking {
        storeDao.insertAll(listOf(store1, store2))
        itemDao.insertAll(listOf(itemA))

        stockStatusRepository.addStockStatusesForItem(itemA.name)

        val result = stockStatusRepository.getAllStockStatusesForItem(itemA.name).first()
        assertThat(result.size, equalTo(2))
        assertThat(result[0].item, equalTo(itemA.name))
        assertThat(result[0].stockStatus, equalTo(StockStatusEnum.UNKNOWN))
        assertThat(result[0].store, equalTo(store1.name))
        assertThat(result[1].item, equalTo(itemA.name))
        assertThat(result[1].stockStatus, equalTo(StockStatusEnum.UNKNOWN))
        assertThat(result[1].store, equalTo(store2.name))
    }

    @Test
    fun testDeleteStockForStores_WhenItemDeleted() = runBlocking {
        storeDao.insertAll(listOf(store1, store2))
        itemDao.insertAll(listOf(itemA, itemB))

        stockStatusRepository.addStockStatusesForItem(itemA.name)
        stockStatusRepository.addStockStatusesForItem(itemB.name)

        val result = stockStatusRepository.getAllStockStatusesForStore(store1.name).first()
        assertThat(result.size, equalTo(2))

        stockStatusRepository.deleteItem(itemA.name)

        val newResult = stockStatusRepository.getAllStockStatusesForStore(store1.name).first()
        assertThat(newResult.size, equalTo(1))
        assertThat(newResult[0].item, equalTo(itemB.name))
    }

    @Test
    fun testDeleteStockForItems_WhenStoreDeleted() = runBlocking {
        storeDao.insertAll(listOf(store1, store2))
        itemDao.insertAll(listOf(itemA))

        stockStatusRepository.addStockStatusesForItem(itemA.name)

        val result = stockStatusRepository.getAllStockStatusesForItem(itemA.name).first()
        assertThat(result.size, equalTo(2))

        stockStatusRepository.deleteStore(store2.name)

        val newResult = stockStatusRepository.getAllStockStatusesForItem(itemA.name).first()
        assertThat(newResult.size, equalTo(1))
        assertThat(newResult[0].store, equalTo(store1.name))
    }
}