package com.semblanceoffunctionality.grocery.ui.grocerylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.GroceryWantedAdapter
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.FragmentGroceriesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroceriesFragment : Fragment() {

    private lateinit var binding: FragmentGroceriesBinding

    private val viewModel: GroceryWantedListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroceriesBinding.inflate(inflater, container, false)
        val adapter = GroceryWantedAdapter()
        adapter.groceryToggle = object : ToggleObtainedCallback {
            override fun toggle(item: Item?) {
                viewModel.toggleObtained(item)
            }
        }
        binding.groceryList.adapter = adapter

        var storeAdapter : ArrayAdapter<String>? =
            this.context?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, ArrayList()) }

        storeAdapter?.add(resources.getString(R.string.no_store_selected))
        storeAdapter?.addAll(viewModel.getStores())
        storeAdapter?.notifyDataSetChanged()

        binding.storeSpinner.adapter = storeAdapter
        binding.storeSelect = object : SelectStore {
            override fun storeSelect() {
                val selection = binding.storeSpinner.selectedItem.toString()
                binding.storeSpinner.setSelection(0)
                if(selection != resources.getString(R.string.no_store_selected)) {
                    navigateToStoreGroceryPage(selection)
                }
            }
        }

        binding.addItem.setOnClickListener {
            navigateToItemListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GroceryWantedAdapter, binding: FragmentGroceriesBinding) {
        viewModel.items.observe(viewLifecycleOwner) { result ->
            binding.hasItems = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    private fun navigateToItemListPage() {
        this.findNavController().navigate(R.id.nav_all_items)
    }

    private fun navigateToStoreGroceryPage(store: String) {
        val action = GroceriesFragmentDirections.actionNavGroceriesToNavStoreGrocery(store)
        this.findNavController().navigate(action)
    }

    interface ToggleObtainedCallback {
        fun toggle(item: Item?)
    }

    interface SelectStore {
        fun storeSelect()
    }
}
