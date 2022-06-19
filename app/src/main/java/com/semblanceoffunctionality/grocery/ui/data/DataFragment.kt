package com.semblanceoffunctionality.grocery.ui.data

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.databinding.FragmentDataBinding
import com.semblanceoffunctionality.grocery.ui.data.DataFragment.WorkerCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DataFragment : Fragment()  {
    private val dataViewModel: DataViewModel by viewModels()
    private lateinit var startForResult: ActivityResultLauncher<Intent?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentDataBinding>(
            inflater,
            R.layout.fragment_data,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            exportCallback = WorkerCallback {
                writeToStorage()

                Snackbar.make(root, R.string.data_exported, Snackbar.LENGTH_LONG)
                    .show()
            }
            importCallback = WorkerCallback {
                Log.e("import", "import")
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Import not yet implemented")
                    .setPositiveButton(R.string.ok
                    ) { _, _ ->
                    }
                val dialog = builder.create()
                dialog.show()
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val jsonContent = dataViewModel.exportData()
                val chosenUri = result.data?.data!!
                val outputStream = context?.contentResolver?.openOutputStream(chosenUri)
                outputStream?.write(jsonContent?.toByteArray())
                outputStream?.close()
            }
        }
    }

    private fun writeToStorage() {
        val relativePath = "Documents/"
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, "forsetiGroceryBackup.json")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse(relativePath))
        }

        startForResult.launch(intent)
    }

    fun interface WorkerCallback {
        fun go()
    }
}