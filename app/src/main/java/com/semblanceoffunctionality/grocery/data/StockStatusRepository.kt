package com.semblanceoffunctionality.grocery.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [StockStatusDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class StockStatusRepository @Inject constructor(
    private val stockStatusDao: StockStatusDao,
    private val itemDao: ItemDao,
    private val storeDao: StoreDao
) {
    fun getStockStatusesForItem(name: String) = stockStatusDao.getStockStatusesForItem(name)

    fun getStockStatus(item: String, store: String) = stockStatusDao.getStatus(item, store)

    fun getAllStockStatusesForStore(store: String) = stockStatusDao.getStockStatusForStore(store)

    suspend fun addStockStatusesForItem(name: String) {
        val stores = storeDao.getStores().first()
        stores.forEach { store ->
            stockStatusDao.insertAll(listOf(StockStatus(name, store.name, StockStatusEnum.UNKNOWN)))
        }
    }

    suspend fun addStockStatusesForStore(store: String) {
        val items = itemDao.getItems().first()
        items.forEach { item ->
            stockStatusDao.insertAll(listOf(StockStatus(item.name, store, StockStatusEnum.UNKNOWN)))
        }
    }

    fun getAllStockStatusesForItem(item: String) = stockStatusDao.getStockStatusesForItem(item)

    fun deleteItem(item: String) = stockStatusDao.deleteItem(item)

    fun deleteStore(item: String) = stockStatusDao.deleteStore(item)

    fun setStockedStatus(store: String, item: String) = stockStatusDao.setStock(item, store, StockStatusEnum.STOCKED)
    fun setUnknownStatus(store: String, item: String) = stockStatusDao.setStock(item, store, StockStatusEnum.UNKNOWN)
    fun setNotStockedStatus(store: String, item: String) = stockStatusDao.setStock(item, store, StockStatusEnum.NOT_STOCKED)
}
