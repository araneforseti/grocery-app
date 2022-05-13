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

    @Query("SELECT * FROM items WHERE name = :name")
    fun getItem(name: String): Flow<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    @Query("UPDATE items SET wanted = 1 WHERE name = :name")
    fun setWanted(name: String)

    @Query("SELECT * FROM items WHERE wanted")
    fun getWantedItems(): Flow<List<Item>>

    @Query("SELECT wanted FROM items WHERE name = :name")
    fun isWanted(name: String): Boolean

    @Query("UPDATE items SET wanted = 0 WHERE name = :name")
    fun setNotWanted(name: String)

    @Query("DELETE from items where name = :name")
    fun deleteItem(name: String)
}
