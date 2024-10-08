package com.example.dartoli.Counting

import android.graphics.Typeface
import com.example.dartoli.Cricket.CricketPlayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.databinding.CountingPlayerItemBinding
import java.lang.Math.round
import kotlin.math.roundToLong

class CountingPlayerStatusAdapter(var datalist:ArrayList<CountingPlayer>, var color: Int, var actualPlayerNumber: Int):RecyclerView.Adapter<CountingPlayerStatusAdapter.PlayerStatusHolder>() {

    var actual_player_number = actualPlayerNumber
    var grey = color
    class PlayerStatusHolder(val binding: CountingPlayerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CountingPlayer, even: Boolean, grey: Int, actual_player: Boolean) {
            if (!even) binding.card.setBackgroundColor(grey)
            if (actual_player) {
                binding.tvPlayername.setTypeface(null, Typeface.BOLD)
                binding.tvPoints.setTypeface(null, Typeface.BOLD)
            }
            binding.tvPlayername.text = get.playerName
            binding.tvPoints.text = get.points.toString()
            binding.tvLegAverage.text = String.format("%.2f", get.leg_average)
            binding.tvGameAverage.text = String.format("%.2f", get.game_average)
            binding.tvDoupleQuote.text = (get.hit_doubles.toString() + "/" + get.throws_on_doubles)
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
        var actual_player: Boolean
        actual_player = (position == actual_player_number)
        if (position % 2 == 0){
            holder.bind(datalist[position], true, grey, actual_player)
        } else {
            holder.bind(datalist[position], false, grey, actual_player)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}