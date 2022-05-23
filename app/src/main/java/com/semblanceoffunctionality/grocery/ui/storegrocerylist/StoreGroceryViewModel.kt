package com.semblanceoffunctionality.grocery.ui.storegrocerylist

import androidx.lifecycle.*
import com.semblanceoffunctionality.grocery.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreGroceryViewModel  @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val groceryStockStatusRepository: StockStatusGroceryItemForStoreRepository,
    private val stockStatusRepository: StockStatusRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {
    val storeName: String = savedStateHandle.get<String>(STORE_SAVED_STATE_KEY)!!
    val items : LiveData<Map<Item, StockStatus>> = groceryStockStatusRepository.getWantedItemsAndStockStatus(storeName).asLiveData()

    init {
        viewModelScope.launch {}
    }

    fun toggleObtained(item: Item?) {
        val newObtained = !item?.obtained!!
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.setObtained(item.name, newObtained)
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
        private const val STORE_SAVED_STATE_KEY = "item"
    }
}
