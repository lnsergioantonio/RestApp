package com.lnsergioantonio.restapp.ui.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ext.onItemSelectedChanged
import com.lnsergioantonio.restapp.ext.value

const val TAG_BODY_DIALOG = "BodyDialog"

class BodyDialog(private val listener: (String, String) -> Unit) : DialogFragment() {

    private var positionItem = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val rootView = inflater.inflate(R.layout.dialog_headers, null)

            val bodyType = resources.getStringArray(R.array.body_type)
            val inputBody: EditText = rootView.findViewById(R.id.inputBody)
            val inputBodyType: Spinner = rootView.findViewById(R.id.bodyType)

            inputBodyType.onItemSelectedChanged { position ->
                positionItem = position
            }

            builder.setView(rootView)
                    .setTitle(R.string.title_add_body)
                    .setPositiveButton(R.string.add_body) { _, _ ->
                        listener.invoke(inputBody.value, bodyType[positionItem])
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
}