package com.semblanceoffunctionality.grocery.ui.stores

import androidx.lifecycle.*
import com.semblanceoffunctionality.grocery.data.Store
import com.semblanceoffunctionality.grocery.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreListViewModel @Inject internal constructor(
    private val storeRepository: StoreRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val stores: LiveData<List<Store>> = storeRepository.getStores().asLiveData()

    init {
        viewModelScope.launch {}
    }

    fun addStore(storeName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            storeRepository.createStore(storeName)
        }
    }
}