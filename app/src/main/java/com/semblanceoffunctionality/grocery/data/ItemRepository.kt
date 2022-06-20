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

    suspend fun createItems(items: List<Item>) = itemDao.insertAll(items)

    fun incrementQuantity(name: String) {
        if(itemDao.isWanted(name))
        {
            itemDao.setQuantity(name, itemDao.getQuantity(name)+1)
        } else {
            itemDao.setWanted(name)
            itemDao.setQuantity(name, 1)
        }
    }

    fun decreaseQuantity(name: String) {
        var newQuantity = itemDao.getQuantity(name)-1
        if(newQuantity <= 0) {
            itemDao.setQuantity(name, 0)
            itemDao.setNotWanted(name)
        } else {
            itemDao.setQuantity(name, newQuantity)
        }

    }

    fun getWantedItems() = itemDao.getWantedItems()

    fun isWanted(name: String) = itemDao.isWanted(name)

    fun deleteItem(name: String) = itemDao.deleteItem(name)

    fun setObtained(name: String?, newObtained: Boolean?) = itemDao.setObtained(name, newObtained)
}
