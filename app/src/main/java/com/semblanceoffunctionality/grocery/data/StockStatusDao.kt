package com.semblanceoffunctionality.grocery.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the ItemsAndStores class.
 */
@Dao
interface StockStatusDao {
    @Query("SELECT * FROM stock_status WHERE itemId = :itemId ORDER BY store")
    fun getStockStatusesForItem(itemId: String): Flow<List<StockStatus>>

    @Query("SELECT * FROM stock_status WHERE store = :store ORDER BY itemId")
    fun getStockStatusForStore(store: String): Flow<List<StockStatus>>

    @TypeConverters(StockConverters::class)
    @Query("SELECT * FROM stock_status WHERE store = :store AND stockStatus = :stockStatus")
    fun getItemsForStoreByStockStatus(store: String, stockStatus: StockStatusEnum): Flow<StockStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stockStatus: List<StockStatus>)

    @Query("UPDATE stock_status SET stockStatus = :stockStatus WHERE store = :store AND itemId = :itemId")
    fun setStock(itemId: String, store: String, stockStatus: StockStatusEnum)

    @Query("DELETE from stock_status where itemId = :itemId AND store = :store")
    fun deleteStockStatus(itemId: String, store: String)

    @Query("DELETE from stock_status where store = :store")
    fun deleteStore(store: String)

    @Query("DELETE from stock_status where itemId = :itemId")
    fun deleteItem(itemId: String)
}
