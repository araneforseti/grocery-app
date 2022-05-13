package com.semblanceoffunctionality.grocery.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the ItemsAndStores class.
 */
@Dao
interface StockStatusDao {
    @Query("SELECT * FROM stock_status WHERE item = :item ORDER BY store")
    fun getStockStatusesForItem(item: String): Flow<List<StockStatus>>

    @Query("SELECT * FROM stock_status WHERE store = :store ORDER BY item")
    fun getStockStatusForStore(store: String): Flow<List<StockStatus>>

    @TypeConverters(StockConverters::class)
    @Query("SELECT * FROM stock_status WHERE store = :store AND stockStatus = :stockStatus")
    fun getItemsForStoreByStockStatus(store: String, stockStatus: StockStatusEnum): Flow<StockStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stockStatus: List<StockStatus>)

    @Query("UPDATE stock_status SET stockStatus = :stockStatus WHERE store = :store AND item = :item")
    fun setStock(item: String, store: String, stockStatus: StockStatusEnum)

    @Query("DELETE from stock_status where item = :item AND store = :store")
    fun deleteStockStatus(item: String, store: String)

    @Query("DELETE from stock_status where store = :store")
    fun deleteStore(store: String)

    @Query("DELETE from stock_status where item = :item")
    fun deleteItem(item: String)
}
