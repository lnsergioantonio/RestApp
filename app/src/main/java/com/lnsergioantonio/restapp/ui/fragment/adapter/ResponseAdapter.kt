package com.lnsergioantonio.restapp.ui.fragment.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ext.inflate
import kotlinx.android.synthetic.main.item_response.view.*

class ResponseAdapter : RecyclerView.Adapter<ResponseHolder>(){
    private var responseList : List<ResponseItem> = emptyList()

    fun setResponseList(list: List<ResponseItem>){
        responseList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseHolder {
        val view = parent.inflate(R.layout.item_response)
        return ResponseHolder(view)
    }

    override fun onBindViewHolder(holder: ResponseHolder, position: Int) {
        holder.renderView(responseList[position])
    }

    override fun getItemCount(): Int = responseList.size
}

class ResponseHolder(view: View): RecyclerView.ViewHolder(view){
    fun renderView(response: ResponseItem) {
        with(itemView) {
            labelCode.text = "${response.statusCode}"
            labelDescription.text = response.description
            labelMethod.text = response.method
            labelSize.text = "${response.size}"
            labelTime.text = response.time
            labelUri.text = response.uri
        }
    }
}