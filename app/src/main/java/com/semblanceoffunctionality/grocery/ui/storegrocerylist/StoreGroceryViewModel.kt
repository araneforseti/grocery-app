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
    private val stockStatusRepository: StockStatusGroceryItemForStoreRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {
    val storeName: String = savedStateHandle.get<String>(STORE_SAVED_STATE_KEY)!!
    val items : LiveData<Map<Item, StockStatus>> = stockStatusRepository.getWantedItemsAndStockStatus(storeName).asLiveData()

    init {
        viewModelScope.launch {}
    }

    fun toggleObtained(item: Item?) {
        val newObtained = !item?.obtained!!
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.setObtained(item.name, newObtained)
        }
    }

    companion object {
        private const val STORE_SAVED_STATE_KEY = "item"
    }
}
