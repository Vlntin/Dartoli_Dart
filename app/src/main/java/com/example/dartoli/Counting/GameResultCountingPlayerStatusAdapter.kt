package com.example.dartoli.Counting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.databinding.GameFinishedItemBinding

class GameResultCountingPlayerStatusAdapter(var datalist:ArrayList<CountingPlayer>): RecyclerView.Adapter<GameResultCountingPlayerStatusAdapter.GameResultPlayerStatusHolder>() {

    class GameResultPlayerStatusHolder(val binding: GameFinishedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CountingPlayer) {
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