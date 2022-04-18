package com.semblanceoffunctionality.groceryapp.data

import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class ItemRepository @Inject constructor(
    private val groceryItemDao: GroceryItemDao
) {
    suspend fun createGroceryItem(name: String) {
        val groceryItem = GroceryItem(name, true)
        groceryItemDao.insertAll(listOf(groceryItem))
    }

    suspend fun updateGroceryItem(name: String, wanted: Boolean) {
        val groceryItem = GroceryItem(name, wanted)
        groceryItemDao.update(groceryItem)
    }

    fun isWanted(name: String) : Boolean {
        return groceryItemDao.isWanted(name)
    }
}