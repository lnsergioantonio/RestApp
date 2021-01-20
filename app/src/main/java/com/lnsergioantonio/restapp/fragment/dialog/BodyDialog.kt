package com.lnsergioantonio.restapp.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ext.value

const val TAG_BODY_DIALOG = "BodyDialog"

class BodyDialog(private val listener: (String) -> Unit) : DialogFragment() {
    private lateinit var inputBody: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val rootView = inflater.inflate(R.layout.dialog_headers, null)
            inputBody = rootView.findViewById(R.id.inputBody)

            builder.setView(rootView)
                    .setView(R.layout.dialog_headers)
                    .setTitle(R.string.title_add_body)
                    .setPositiveButton(R.string.add_body) { _, _ ->

                    }
                    .setNegativeButton(R.string.cancel) { dialog, id ->
                        dialog.cancel()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun show(parentFragmentManager: FragmentManager) {
        val prevDialog = parentFragmentManager.findFragmentByTag(TAG_BODY_DIALOG)
        if (prevDialog == null)
            show(parentFragmentManager, TAG_BODY_DIALOG)
    }

    override fun onResume() {
        super.onResume()
        val d = dialog as AlertDialog?
        if (d != null) {
            val positiveButton: Button = d.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                listener(inputBody.value)
                d.dismiss()
            }
        }
    }
}