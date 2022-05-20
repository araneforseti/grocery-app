package com.semblanceoffunctionality.grocery.ui.itemdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.semblanceoffunctionality.grocery.data.ItemRepository
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [ItemDetailFragment].
 */
@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository,
    private val stockStatusRepository: StockStatusRepository
) : ViewModel() {

    val name: String = savedStateHandle.get<String>(ITEM_ID_SAVED_STATE_KEY)!!
    val item = itemRepository.getItem(name).asLiveData()
    val statuses = stockStatusRepository.getStockStatusesForItem(name).asLiveData()

    fun addItemToGrocery() {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.incrementQuantity(name)
        }
    }

    fun removeItemFromGrocery() {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.decreaseQuantity(name)
        }
    }

    fun deleteItem() {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.deleteItem(name)
            itemRepository.deleteItem(name)
        }
    }

    fun setStockedStatus(store: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.setStockedStatus(store, name)
        }
    }

    fun setStockedUnknownStatus(store: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.setUnknownStatus(store, name)
        }
    }

    fun setNotStockedStatus(store: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stockStatusRepository.setNotStockedStatus(store, name)
        }
    }

    fun getStatusForStore(store: String): StockStatus? {
        return stockStatusRepository.getStockStatus(name, store)
    }

    companion object {
        private const val ITEM_ID_SAVED_STATE_KEY = "name"
    }
}
