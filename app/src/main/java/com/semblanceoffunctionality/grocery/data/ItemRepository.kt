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

    fun getItem(name: String) = itemDao.getItem(name)

    suspend fun createItem(itemName: String) = itemDao.insertAll(listOf(Item(itemName, true)))

    fun setWanted(name: String) {
        itemDao.setWanted(name)
    }

    fun getWantedItems() = itemDao.getWantedItems()

    fun isWanted(name: String) = itemDao.isWanted(name)

    fun removeWanted(name: String) = itemDao.setNotWanted(name)

    fun deleteItem(name: String) = itemDao.deleteItem(name)
}
