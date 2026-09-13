package com.example.storewheel.commons

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.storewheel.databinding.ViewErrorDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Fragment.showErrorDialog(): AlertDialog {
    val dialogBinding = ViewErrorDialogBinding.inflate(layoutInflater)
    val dialog = MaterialAlertDialogBuilder(requireContext()).setView(dialogBinding.root).create()
    dialogBinding.confirmation.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
    return dialog
}
