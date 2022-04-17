package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface GroceryItemDao{
    @Query("SELECT * FROM groceryitem")
    fun getAll(): List<GroceryItem>

    @Query("SELECT * FROM groceryitem WHERE wanted=TRUE")
    fun getAllWantedItems(): List<GroceryItem>

    @Query("SELECT * FROM groceryitem WHERE name = :name")
    fun findByName(name: String)

    @Insert
    fun insertAll(items: List<GroceryItem>)

    @Update
    fun update(item: GroceryItem)

    @Delete
    fun delete(item: GroceryItem)
}