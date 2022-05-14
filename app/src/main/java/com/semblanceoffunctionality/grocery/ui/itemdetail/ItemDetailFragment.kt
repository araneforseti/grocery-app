package com.semblanceoffunctionality.grocery.ui.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.ItemStockAdapter
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.FragmentItemDetailBinding
import com.semblanceoffunctionality.grocery.generated.callback.OnClickListener
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusRadioButton
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a single Item detail screen.
 */
@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private val itemDetailViewModel: ItemDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentItemDetailBinding>(
            inflater,
            R.layout.fragment_item_detail,
            container,
            false
        ).apply {
            viewModel = itemDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            addCallback = AddCallback { item ->
                item?.let {
                    itemDetailViewModel.addItemToGrocery()
                    Snackbar.make(root, R.string.added_item_to_grocery, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            removeCallback = RemoveCallback { item ->
                item?.let {
                    itemDetailViewModel.removeItemFromGrocery()
                    Snackbar.make(root, R.string.removed_item_from_grocery, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            deleteCallback = DeleteCallback { item ->
                item?.let {
                    itemDetailViewModel.deleteItem()
                    Snackbar.make(root, R.string.deleted_item, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
        setHasOptionsMenu(true)

        val adapter = ItemStockAdapter(
            object : ItemStockAdapter.StockedClickListener {
                override fun onClickListener(store: String) {
                    itemDetailViewModel.setStockedStatus(store)
                }
            },
            object : ItemStockAdapter.UnknownClickListener {
                override fun onClickListener(store: String) {
                    itemDetailViewModel.setStockedUnknownStatus(store)
                }
            },
            object : ItemStockAdapter.NotStockedClickListener {
                override fun onClickListener(store: String) {
                    itemDetailViewModel.setNotStockedStatus(store)
                }
            },
        )
        binding.stockList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ItemStockAdapter) {
        itemDetailViewModel.statuses.observe(viewLifecycleOwner) { statuses ->
            adapter.submitList(statuses)
        }
    }

    fun interface AddCallback {
        fun add(item: Item?)
    }

    fun interface RemoveCallback {
        fun remove(item: Item?)
    }

    fun interface DeleteCallback {
        fun delete(item: Item?)
    }
}
