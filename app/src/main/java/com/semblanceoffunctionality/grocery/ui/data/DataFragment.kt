package com.semblanceoffunctionality.grocery.ui.data

import android.Manifest
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.Store
import com.semblanceoffunctionality.grocery.databinding.FragmentDataBinding
import kotlinx.coroutines.NonCancellable.start

class DataFragment : Fragment()  {
    private val dataViewModel: DataViewModel by viewModels()

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
                Log.e("export", "export")
                if(isStoragePermissionGranted()) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Export not yet implemented")
                        .setPositiveButton(R.string.ok,
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    alertPermission()
                }
            }
            importCallback = WorkerCallback {
                Log.e("import", "import")
                if(isStoragePermissionGranted()) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Import not yet implemented")
                        .setPositiveButton(R.string.ok,
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    alertPermission()
                }
            }
        }

        return binding.root
    }

    private fun alertPermission() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.no_permission)
            .setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                })
        val dialog = builder.create()
        dialog.show()
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
                //Permission is granted
                true
            } else {
                //Permission is revoked
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                false
            }
        } else {
            // Permission is automatically granted on sdk<23 upon installation
            true
        }
    }

    fun interface WorkerCallback {
        fun go()
    }
}