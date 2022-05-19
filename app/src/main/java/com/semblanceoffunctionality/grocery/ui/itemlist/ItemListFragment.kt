package com.semblanceoffunctionality.grocery.ui.itemlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.ItemAdapter
import com.semblanceoffunctionality.grocery.databinding.FragmentGroceriesBinding
import com.semblanceoffunctionality.grocery.databinding.FragmentItemListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment() {
    private val viewModel: ItemListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentItemListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = ItemAdapter()
        binding.itemList.adapter = adapter
        subscribeUi(adapter, binding)

        binding.addItem.setOnClickListener {
            createItemDialog(container)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_list, menu)
    }

    private fun subscribeUi(adapter: ItemAdapter, binding: FragmentItemListBinding) {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            binding.hasItems = !items.isNullOrEmpty()
            adapter.submitList(items)
        }
    }

    private fun createItemDialog(container: ViewGroup?) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_new_item_dialog, container, false)
            builder.apply {
                setView(dialogView)
                setPositiveButton(R.string.create) { _, _ ->
                    val userInput = dialogView.findViewById<EditText>(R.id.new_item)
                    viewModel.addItem(userInput.text.toString())
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog?.cancel()
                }
            }
            builder.create()
        }?.show()
    }
}
