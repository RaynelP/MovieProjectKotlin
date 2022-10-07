package com.raynel.alkemyproject.view.principalActivity.user

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.view.principalActivity.MainActivity

class OnSignOutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("¿Are you sure do you want logout?")
                .setPositiveButton("Log out",
                    DialogInterface.OnClickListener { dialog, id ->
                        val activity = requireActivity() as MainActivity
                        activity.signUp()
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            // Create the AlertDialog object and return it
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")

    }
}
