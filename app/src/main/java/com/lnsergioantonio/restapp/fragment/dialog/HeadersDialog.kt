package com.lnsergioantonio.restapp.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.lnsergioantonio.restapp.R

class HeadersDialog : DialogFragment() {
    private var headersItem: List<HeadersItem> = emptyList()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                setPositiveButton(R.string.ok, { dialogInterface, id ->

                })
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}