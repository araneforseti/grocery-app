package com.semblanceoffunctionality.grocery.ui.data

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.semblanceoffunctionality.grocery.data.ItemRepository
import com.semblanceoffunctionality.grocery.data.StockStatusRepository
import com.semblanceoffunctionality.grocery.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * The ViewModel used in [DataFragment].
 */
@HiltViewModel
class DataViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val itemRepository: ItemRepository,
    private val stockStatusRepository: StockStatusRepository
)  : ViewModel() {

    private var gson: Gson? = null

    fun exportData() = runBlocking {
        gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()

        val stores = storeRepository.getStores().first()
        val items = itemRepository.getItems().first()
        val stocks = stockStatusRepository.getAll().first()

        val allData = Data(items, stores, stocks)
        return@runBlocking gson?.toJson(allData)
    }
}