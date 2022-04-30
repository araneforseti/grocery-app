package com.semblanceoffunctionality.grocery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.semblanceoffunctionality.grocery.ItemListFragment
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.data.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for [ItemListFragment].
 */
@HiltViewModel
class ItemListViewModel @Inject internal constructor(
    private val itemRepository: ItemRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val items: LiveData<List<Item>> = itemRepository.getItems().asLiveData()

    init {
        viewModelScope.launch {}
    }

    fun addItem(itemName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.createItem(itemName)
        }
    }

    companion object {
        private const val NO_STORE = -1
        private const val STORE_SAVED_STATE_KEY = "STORE_SAVED_STATE_KEY"
    }
}
