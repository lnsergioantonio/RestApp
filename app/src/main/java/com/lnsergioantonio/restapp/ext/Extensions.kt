package com.lnsergioantonio.restapp.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import com.google.android.material.textfield.TextInputEditText

fun ViewGroup.layoutInflater(): LayoutInflater {
    return LayoutInflater.from(context)
}

fun ViewGroup.inflate(layoutId:Int): View {
    return context.inflateLayout(layoutId,this,false)
}

fun Context?.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

val TextInputEditText.value
    get() = text.toString()

val EditText.value
    get() = text.toString()
