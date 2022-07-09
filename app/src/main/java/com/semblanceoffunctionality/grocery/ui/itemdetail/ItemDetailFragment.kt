package com.semblanceoffunctionality.grocery.ui.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.ItemStockAdapter
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.FragmentItemDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    val action = ItemDetailFragmentDirections.actionNavItemDetailToNavAllItems()
                    findNavController().navigate(action)
                }
            }
            editNameCallback = EditNameCallback {
                editNameDialog(container)
            }
        }
        setHasOptionsMenu(true)

        val adapter = ItemStockAdapter(itemDetailViewModel)
        binding.stockList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ItemStockAdapter) {
        itemDetailViewModel.statuses.observe(viewLifecycleOwner) { statuses ->
            adapter.submitList(statuses)
        }
    }

    private fun editNameDialog(container: ViewGroup?) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_edit_name_dialog, container, false)
            builder.apply {
                setView(dialogView)
                setPositiveButton(R.string.create) { _, _ ->
                    val userInput = dialogView.findViewById<EditText>(R.id.new_name)
                    CoroutineScope(Dispatchers.IO).launch {
                        itemDetailViewModel.updateName(userInput.text.toString())
                    }
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog?.cancel()
                }
            }
            builder.create()
        }?.show()
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

    fun interface EditNameCallback {
        fun edit()
    }
}
