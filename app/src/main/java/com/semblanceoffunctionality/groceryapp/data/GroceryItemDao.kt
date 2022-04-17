package com.semblanceoffunctionality.groceryapp.data

import androidx.room.*

@Dao
interface GroceryItemDao{
    @Query("SELECT * FROM groceryitem")
    fun getAll(): List<GroceryItem>

    @Query("SELECT * FROM groceryitem WHERE wanted=TRUE")
    fun getAllWantedItems(): List<GroceryItem>

    @Query("SELECT * FROM groceryitem WHERE name = :name")
    fun findByName(name: String): GroceryItem

    @Insert
    fun insertAll(items: List<GroceryItem>)

    @Update
    fun update(item: GroceryItem)

    @Delete
    fun delete(item: GroceryItem)
}