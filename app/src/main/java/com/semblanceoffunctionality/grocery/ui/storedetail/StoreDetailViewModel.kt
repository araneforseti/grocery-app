package com.semblanceoffunctionality.grocery.ui.storedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusRepository
import com.semblanceoffunctionality.grocery.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [StoreDetailFragment].
 */
@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val storeRepository: StoreRepository,
    private val stockStatusRepository: StockStatusRepository
) : ViewModel() {

    private var storeName: String = savedStateHandle.get<String>(STORE_SAVED_STATE_KEY)!!
    val store = storeRepository.getStore(storeName).asLiveData()
    val statuses = stockStatusRepository.getAllStockStatusesForStore(storeName).asLiveData()

    fun deleteStore() {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.deleteStore(storeName)
            storeRepository.deleteStore(storeName)
        }
    }

    // I regret making name the constraint, then allowing name to be edited
    fun updateName(toString: String) {
        CoroutineScope(Dispatchers.IO).launch {
            storeRepository.duplicateStore(toString, storeName)
            stockStatusRepository.duplicateStore(toString, storeName)
            stockStatusRepository.deleteStore(storeName)
            storeRepository.deleteStore(storeName)
            savedStateHandle.set(STORE_SAVED_STATE_KEY, toString)
            storeName = toString
        }
    }

    fun setStockedStatus(item: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.setStockedStatus(storeName, item)
        }
    }

    fun setStockedUnknownStatus(item: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.setUnknownStatus(storeName, item)
        }
    }

    fun setNotStockedStatus(item: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.setNotStockedStatus(storeName, item)
        }
    }

    fun getStatusForItem(item: String): StockStatus? {
        return stockStatusRepository.getStockStatus(item, storeName)
    }

    companion object {
        private const val STORE_SAVED_STATE_KEY = "store"
    }
}
