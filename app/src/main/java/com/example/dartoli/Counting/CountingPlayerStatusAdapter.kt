package com.example.dartoli.Counting

import com.example.dartoli.Cricket.CricketPlayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.databinding.CountingPlayerItemBinding

class CountingPlayerStatusAdapter(var datalist:ArrayList<CountingPlayer>):RecyclerView.Adapter<CountingPlayerStatusAdapter.PlayerStatusHolder>() {

    class PlayerStatusHolder(val binding: CountingPlayerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CountingPlayer) {
            binding.tvPlayername.text = get.playerName
            binding.tvPoints.text = get.points.toString()
            binding.tvAverage.text = get.average.toString()

            binding.tvLegs.setText(get.won_legs.toString() + "/" + get.needed_legs)
            binding.tvSets.setText(get.won_sets.toString() + "/" + get.needed_sets)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatusHolder {
        val binding = CountingPlayerItemBinding
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