package com.lnsergioantonio.restapp.ext

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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

fun View.getColor(@ColorRes id:Int):Int{
    return ContextCompat.getColor(context, id)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Context.launchActivity (
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

