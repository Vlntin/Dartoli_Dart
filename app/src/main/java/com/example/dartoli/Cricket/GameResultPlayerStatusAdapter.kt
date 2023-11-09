package com.example.dartoli.Cricket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.databinding.GameFinishedItemBinding

class GameResultPlayerStatusAdapter(var datalist:ArrayList<CricketPlayer>): RecyclerView.Adapter<GameResultPlayerStatusAdapter.GameResultPlayerStatusHolder>() {

    class GameResultPlayerStatusHolder(val binding: GameFinishedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CricketPlayer) {
            binding.tvPlayerNamePlace.text = get.rank.toString() + ". " + get.playerName
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