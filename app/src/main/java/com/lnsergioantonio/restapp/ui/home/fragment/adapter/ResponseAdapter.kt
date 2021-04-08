package com.lnsergioantonio.restapp.ui.home.fragment.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ext.getColor
import com.lnsergioantonio.restapp.ext.inflate
import kotlinx.android.synthetic.main.item_response.view.*

class ResponseAdapter :
    ListAdapter<ResponseItem, ResponseHolder>(DiffUtilCallback) { // RecyclerView.Adapter<ResponseHolder>(){

    private var listener: (item: ResponseItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseHolder {
        val view = parent.inflate(R.layout.item_response)
        return ResponseHolder(view)
    }

    override fun onBindViewHolder(holder: ResponseHolder, position: Int) {
        val responseItem = getItem(position)
        holder.renderView(responseItem)
        holder.setClickItem(responseItem, listener)
    }

    fun setOnClickItem(listener: (item: ResponseItem) -> Unit) {
        this.listener = listener
    }

}

class ResponseHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun renderView(response: ResponseItem) {
        with(itemView) {
            labelMethod.text = response.method
            labelUri.text = response.uri

            labelCode.text = context.getString(
                R.string.response_adapter_label_code,
                response.statusCode
            )

            labelTime.text = context.getString(R.string.response_adapter_label_time, response.time)
            labelSize.text = context.getString(R.string.response_adapter_label_size, response.size)

            if (response.isSuccessful)
                labelCode.setTextColor(getColor(android.R.color.holo_green_dark))
            else
                labelCode.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }

    fun setClickItem(item: ResponseItem, listener: (item: ResponseItem) -> Unit) {
        itemView.setOnClickListener { listener.invoke(item) }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<ResponseItem>() {
    override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean =
        oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean =
        oldItem == newItem

}