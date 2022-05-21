package com.semblanceoffunctionality.grocery.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockStatusGroceryItemForStoreRepository @Inject constructor(
    private val stockStatusGroceryDao: StockStatusGroceryItemForStoreDao
) {
    fun getWantedItemsAndStockStatus(storeName: String) : Flow<Map<Item, StockStatus>> =
        stockStatusGroceryDao.getWantedItemsAndStockStatus(storeName)

}