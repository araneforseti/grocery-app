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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.databinding.FragmentDataBinding
import com.semblanceoffunctionality.grocery.ui.data.DataFragment.WorkerCallback
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader


@AndroidEntryPoint
class DataFragment : Fragment()  {
    private val dataViewModel: DataViewModel by viewModels()
    private lateinit var chooseFileForExportLauncher: ActivityResultLauncher<Intent?>
    private lateinit var openFileResultLauncher: ActivityResultLauncher<Intent?>

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
                chooseFileForExport()

                Snackbar.make(root, R.string.data_exported, Snackbar.LENGTH_LONG)
                    .show()
            }
            importCallback = WorkerCallback {
                chooseFileForImport()

                Snackbar.make(root, R.string.data_imported, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chooseFileForExportLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val jsonContent = dataViewModel.exportData()
                val chosenUri = result.data?.data!!
                val outputStream = context?.contentResolver?.openOutputStream(chosenUri)
                outputStream?.write(jsonContent?.toByteArray())
                outputStream?.close()
            }
        }

        openFileResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val chosenUri = result.data?.data!!
                val inputStream = context?.contentResolver?.openInputStream(chosenUri)
                val content = inputStream?.bufferedReader()?.use(BufferedReader::readText)
                dataViewModel.importData(content!!)
            }
        }
    }

    private fun chooseFileForExport() {
        val relativePath = "Documents/"
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, "forsetiGroceryBackup.json")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse(relativePath))
        }

        chooseFileForExportLauncher.launch(intent)
    }

    private fun chooseFileForImport() {
        val relativePath = "Documents/"

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse(relativePath))
        }

        openFileResultLauncher.launch(intent)
    }

    fun interface WorkerCallback {
        fun go()
    }
}