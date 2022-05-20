package com.semblanceoffunctionality.grocery.ui.storegrocerylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.data.ItemRepository
import com.semblanceoffunctionality.grocery.data.StockStatusRepository
import com.semblanceoffunctionality.grocery.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreGroceryViewModel  @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository,
    private val storeRepository: StoreRepository,
    private val stockStatusRepository: StockStatusRepository
) : ViewModel() {
    private val storeName: String = savedStateHandle.get<String>(STORE_SAVED_STATE_KEY)!!
    val store = storeRepository.getStore(storeName).asLiveData()
    val items: LiveData<List<Item>> = itemRepository.getWantedItems().asLiveData()
    val statuses = stockStatusRepository.getAllStockStatusesForStore(storeName).asLiveData()

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
