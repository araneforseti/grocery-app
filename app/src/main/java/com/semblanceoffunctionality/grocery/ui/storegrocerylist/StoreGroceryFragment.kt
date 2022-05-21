package com.semblanceoffunctionality.grocery.ui.storegrocerylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.semblanceoffunctionality.grocery.adapters.StoreGroceryAdapter
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.FragmentStoreGroceryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreGroceryFragment : Fragment() {

    private lateinit var binding: FragmentStoreGroceryBinding

    private val storeGroceryViewModel: StoreGroceryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreGroceryBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = storeGroceryViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val adapter = StoreGroceryAdapter()
        if (adapter != null) {
            adapter.groceryToggle = object : ToggleObtainedCallback {
                override fun toggle(item: Item?) {
                    storeGroceryViewModel.toggleObtained(item)
                }
            }
            subscribeUi(adapter, binding)
        }

        binding.storeGroceryList.adapter = adapter

        return binding.root
    }

    private fun subscribeUi(adapter: StoreGroceryAdapter, binding: FragmentStoreGroceryBinding) {
        storeGroceryViewModel.items.observe(viewLifecycleOwner) { result ->
            binding.hasItems = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    interface ToggleObtainedCallback {
        fun toggle(item: Item?)
    }
}
