package com.semblanceoffunctionality.groceryapp.data

import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class StoreRepository @Inject constructor(
    private val storeDao: StoreDao
) {
    suspend fun createStore(name: String) {
        val store = Store(name, "")
        storeDao.insertAll(listOf(store))
    }

    suspend fun updateStore(name: String, address: String) {
        val store = Store(name, address)
        storeDao.update(store)
    }

    fun getAddress(name: String) : String {
        return storeDao.getAddress(name)
    }
}