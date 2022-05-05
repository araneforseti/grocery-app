package com.semblanceoffunctionality.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.semblanceoffunctionality.grocery.adapters.GroceryWantedAdapter
import com.semblanceoffunctionality.grocery.adapters.ITEM_LIST_PAGE_INDEX
import com.semblanceoffunctionality.grocery.databinding.FragmentGroceryBinding
import com.semblanceoffunctionality.grocery.viewmodels.GroceryWantedListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryFragment : Fragment() {

    private lateinit var binding: FragmentGroceryBinding

    private val viewModel: GroceryWantedListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroceryBinding.inflate(inflater, container, false)
        val adapter = GroceryWantedAdapter()
        binding.groceryList.adapter = adapter

        binding.addItem.setOnClickListener {
            navigateToItemListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GroceryWantedAdapter, binding: FragmentGroceryBinding) {
        viewModel.items.observe(viewLifecycleOwner) { result ->
            binding.hasItemings = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToItemListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            ITEM_LIST_PAGE_INDEX
    }
}
