
package com.example.android.exampleTest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var model = emptyList<Model>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tv_number)
        val tv_name: TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = model[position]
        holder.tvNumber.text = current.number
        holder.tv_name.text = current.name
    }

        internal fun setWords(models: List<Model>) {
        this.model = models
        notifyDataSetChanged()
    }

    override fun getItemCount() = model.size
}


