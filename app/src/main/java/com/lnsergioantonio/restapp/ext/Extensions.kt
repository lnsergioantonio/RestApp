package com.lnsergioantonio.restapp.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
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

inline fun Spinner.onItemSelectedChanged(crossinline onItemSelected:(Int)->Unit){
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            onItemSelected(pos)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }
}