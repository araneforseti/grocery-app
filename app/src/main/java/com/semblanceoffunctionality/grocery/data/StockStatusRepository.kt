package com.semblanceoffunctionality.grocery.data

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
    fun getStockStatusesForItem(itemId: String) = stockStatusDao.getStockStatusesForItem(itemId)

    fun getAllStockStatusesForStore(store: String) = stockStatusDao.getStockStatusForStore(store)

    suspend fun addStockStatusesForItem(itemId: String) {
        val stores = storeDao.getStores().first()
        stores.forEach { store ->
            stockStatusDao.insertAll(listOf(StockStatus(itemId, store.name, StockStatusEnum.UNKNOWN)))
        }
    }

    suspend fun addStockStatusesForStore(store: String) {
        val items = itemDao.getItems().first()
        items.forEach { item ->
            stockStatusDao.insertAll(listOf(StockStatus(item.itemId, store, StockStatusEnum.UNKNOWN)))
        }
    }

    fun getAllStockStatusesForItem(itemId: String) = stockStatusDao.getStockStatusesForItem(itemId)
}
