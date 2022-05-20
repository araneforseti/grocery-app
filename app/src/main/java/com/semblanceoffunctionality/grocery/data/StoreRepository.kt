package com.semblanceoffunctionality.grocery.data

import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [StoreDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class StoreRepository @Inject constructor(
    private val storeDao: StoreDao
) {

    fun getStores() = storeDao.getStores()

    fun getStore(name: String) = storeDao.getStore(name)

    suspend fun createStore(name: String, address: String = "") = storeDao.insertAll(listOf(Store(name, address)))

    fun deleteStore(name: String) = storeDao.deleteStore(name)

    suspend fun getStoresNames() : List<String> = storeDao.getStoreNames().first()
}
