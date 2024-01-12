package com.developer404.myppt.others

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer404.myppt.R

class ListAdapter: RecyclerView.Adapter<ListAdapter.holder> {

    lateinit var list:ArrayList<modal>
    lateinit var context: Context
    lateinit var face: RVitem

    constructor(list: ArrayList<modal>, context: Context, face: RVitem) : super() {
        this.list = list
        this.context = context
        this.face=face
    }


    class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.itrm_rv_txt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_rv,parent,false)
        return holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        holder.name.text=list[position].name
        holder.itemView.setOnClickListener {
            face.onclick(position)
        }

    }
}