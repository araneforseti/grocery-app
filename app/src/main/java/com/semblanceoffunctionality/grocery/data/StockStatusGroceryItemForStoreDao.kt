package com.semblanceoffunctionality.grocery.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@TypeConverters(StockConverters::class)
@Dao
abstract class StockStatusGroceryItemForStoreDao {
    @Query("SELECT * FROM items " +
            "JOIN stock_status ON stock_status.item = items.name " +
            "WHERE stock_status.store = :storeName AND items.wanted " +
            "ORDER BY items.obtained ASC, stock_status.stockStatus ASC, items.name ASC")
    abstract fun getWantedItemsAndStockStatus(storeName: String): Flow<Map<Item, StockStatus>>

}