package com.semblanceoffunctionality.grocery.ui.grocerylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.data.ItemRepository
import com.semblanceoffunctionality.grocery.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GroceryWantedListViewModel @Inject internal constructor(
    private val itemRepository: ItemRepository,
    private val storeRepository: StoreRepository
) : ViewModel() {
    val items: LiveData<List<Item>> = itemRepository.getWantedItems().asLiveData()

    fun toggleObtained(item: Item?) {
        val newObtained = !item?.obtained!!
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.setObtained(item.name, newObtained)
        }
    }

    fun getStores(): List<String> = runBlocking {
        return@runBlocking storeRepository.getStoresNames()
    }
}
