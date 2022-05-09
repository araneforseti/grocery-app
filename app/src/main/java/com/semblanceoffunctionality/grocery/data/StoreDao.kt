package com.semblanceoffunctionality.grocery.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Store class.
 */
@Dao
interface StoreDao {
    @Query("SELECT * FROM stores ORDER BY name")
    fun getStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE name = :name")
    fun getStore(name: String): Flow<Store>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<Store>)

    @Query("DELETE from stores where name = :name")
    fun deleteStore(name: String)
}
