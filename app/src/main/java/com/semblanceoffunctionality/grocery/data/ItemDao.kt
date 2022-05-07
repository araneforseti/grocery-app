package com.semblanceoffunctionality.grocery.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Item class.
 */
@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY name")
    fun getItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :itemId")
    fun getItem(itemId: String): Flow<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    @Query("UPDATE items SET wanted = 1 WHERE id = :itemId")
    fun setWanted(itemId: String)

    @Query("SELECT * FROM items WHERE wanted")
    fun getWantedItems(): Flow<List<Item>>

    @Query("SELECT wanted FROM items WHERE id = :itemId")
    fun isWanted(itemId: String): Boolean

    @Query("UPDATE items SET wanted = 0 WHERE id = :itemId")
    fun setNotWanted(itemId: String)

    @Query("DELETE from items where id = :itemId")
    fun deleteItem(itemId: String)
}
