package com.example.dartoli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.databinding.ListItemBinding
import com.example.dartoli.model.Player
/**
class PlayerStatusAdapter(var items: ArrayList<Player>, val context: Context):
    RecyclerView.Adapter<PlayerStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatusAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerStatusAdapter.ViewHolder, position: Int) {
        val model: Player = items[position]

        holder
        //holder.tvItem.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val tvItem = view.tvItem!!
        val tvName = view.
    }

}*/

class PlayerStatusAdapter(var datalist:ArrayList<Player>):RecyclerView.Adapter<PlayerStatusAdapter.PlayerStatusHolder>() {

    class PlayerStatusHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: Player) {
            binding.tvPlayername.text = get.playerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatusHolder {
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerStatusHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerStatusHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}