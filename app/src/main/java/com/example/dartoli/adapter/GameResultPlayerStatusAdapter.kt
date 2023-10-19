package com.example.dartoli.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.databinding.GameFinishedItemBinding
import com.example.dartoli.databinding.ListItemBinding
import com.example.dartoli.model.CricketPlayer

class GameResultPlayerStatusAdapter(var datalist:ArrayList<CricketPlayer>): RecyclerView.Adapter<GameResultPlayerStatusAdapter.GameResultPlayerStatusHolder>() {

    class GameResultPlayerStatusHolder(val binding: GameFinishedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CricketPlayer) {
            binding.tvPlayerNamePlace.text = get.place.toString() + ". " + get.playerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultPlayerStatusHolder {
        val binding = GameFinishedItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultPlayerStatusHolder(binding)
    }

    override fun onBindViewHolder(holder: GameResultPlayerStatusHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}