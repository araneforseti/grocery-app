package com.semblanceoffunctionality.grocery.ui.storedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.StoreStockAdapter
import com.semblanceoffunctionality.grocery.data.StockStatusEnum
import com.semblanceoffunctionality.grocery.data.Store
import com.semblanceoffunctionality.grocery.databinding.FragmentStoreDetailBinding
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusRadioGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Store detail screen.
 */
@AndroidEntryPoint
class StoreDetailFragment : Fragment() {

    private val storeDetailViewModel: StoreDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentStoreDetailBinding>(
            inflater,
            R.layout.fragment_store_detail,
            container,
            false
        ).apply {
            viewModel = storeDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            deleteCallback = DeleteCallback { store ->
                store?.let {
                    storeDetailViewModel.deleteStore()
                    Snackbar.make(root, R.string.deleted_store, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            editNameCallback = EditNameCallback {
                editNameDialog(container)
            }
        }
        setHasOptionsMenu(true)

        val adapter = StoreStockAdapter(storeDetailViewModel)
        binding.stockItemList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
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
                        storeDetailViewModel.updateName(userInput.text.toString())
                    }
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog?.cancel()
                }
            }
            builder.create()
        }?.show()
    }

    private fun subscribeUi(adapter: StoreStockAdapter) {
        storeDetailViewModel.statuses.observe(viewLifecycleOwner) { statuses ->
            adapter.submitList(statuses)
        }
    }

    fun interface DeleteCallback {
        fun delete(store: Store?)
    }

    fun interface EditNameCallback {
        fun edit()
    }
}
