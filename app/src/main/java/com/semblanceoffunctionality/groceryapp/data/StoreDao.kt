package com.semblanceoffunctionality.groceryapp.data

import androidx.room.*

@Dao
interface StoreDao{
    @Query("SELECT * FROM store")
    fun getAll(): List<Store>

    @Query("SELECT * FROM store WHERE name = :name")
    fun findByName(name: String): Store

    @Query("SELECT address FROM store WHERE name = :name")
    fun getAddress(name: String) : String

    @Insert
    fun insertAll(items: List<Store>)

    @Update
    fun update(item: Store)

    @Delete
    fun delete(item: Store)
}