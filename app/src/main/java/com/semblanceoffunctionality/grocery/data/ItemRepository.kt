package com.semblanceoffunctionality.grocery.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [ItemDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    fun getItems() = itemDao.getItems()

    fun getItem(itemId: String) = itemDao.getItem(itemId)

    suspend fun createItem(itemName: String) = itemDao.insertAll(listOf(Item(itemName, itemName, true)))

    fun setWanted(itemId: String) {
        itemDao.setWanted(itemId)
    }

    fun getWantedItems() = itemDao.getWantedItems()

    fun isWanted(itemId: String) = itemDao.isWanted(itemId)

    fun removeWanted(itemId: String) = itemDao.setNotWanted(itemId)

    fun deleteItem(itemId: String) = itemDao.deleteItem(itemId)
}
