package com.raynel.alkemyproject.view.principalActivity.user

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.raynel.alkemyproject.R

class showActualImageOrLoadImageDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setItems(
                    R.array.options_image,
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                    })
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")

    }
}