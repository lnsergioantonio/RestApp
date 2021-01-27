package com.lnsergioantonio.restapp.ui.fragment.dialog

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ext.inflate
import com.lnsergioantonio.restapp.ui.header.adapter.HeadersItem
import kotlinx.android.synthetic.main.item_header.view.*

class HeadersAdapter : RecyclerView.Adapter<HeadersHolder>() {
    private var headerList: List<HeadersItem> = emptyList()

    fun setHeaderList(list: List<HeadersItem>){
        headerList = list
        notifyDataSetChanged()
    }

    fun addHeader(header: HeadersItem){
        headerList = headerList.toMutableList().apply { add(header) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadersHolder {
        val view = parent.inflate(R.layout.item_header)
        return HeadersHolder(view)
    }

    override fun onBindViewHolder(holder: HeadersHolder, position: Int) {
        holder.renderView(headerList[position])
        holder.setListener(position){ index ->
            headerList.toMutableList().removeAt(index)
        }
    }

    override fun getItemCount(): Int = headerList.size

    fun getItems(): ArrayList<HeadersItem> {
        return ArrayList(headerList)
    }
}

class HeadersHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun renderView(header: HeadersItem) {
        with(itemView) {
            labelKey.text = header.key
            labelValue.text = header.value
        }
    }

    fun setListener(position: Int ,listener:(index:Int) ->Unit){
        itemView.buttonDeleteHeader.setOnClickListener {
            listener.invoke(position)
        }
    }
}