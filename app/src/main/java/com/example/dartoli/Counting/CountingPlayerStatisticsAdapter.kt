package com.example.dartoli.Counting

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.databinding.CountingStatisticsItemBinding

class CountingPlayerStatisticsAdapter(var datalist:ArrayList<CountingPlayer>, var color: Int): RecyclerView.Adapter<CountingPlayerStatisticsAdapter.PlayerStatusHolder>() {
    var grey = color
    class PlayerStatusHolder(val binding: CountingStatisticsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CountingPlayer, even: Boolean, grey: Int) {
            if (!even) binding.card.setBackgroundColor(grey)
            binding.tvPlayername.text = get.playerName
            binding.tvRank.text = get.rank.toString()
            binding.tvGameAverage.text = String.format("%.2f", get.game_average)
            binding.tvHighestScore.text = get.all_three_throws_points.max().toString()
            binding.tvDoubleQuote.text = get.hit_doubles.toString() + "/" + get.throws_on_doubles.toString()
            binding.tvHighestScore.text = get.all_three_throws_points.max().toString()
            binding.tvLegBestAverage.text = String.format("%.2f", get.all_averages.max())
            binding.tvLegBadestAverage.text = String.format("%.2f", get.all_averages.min())
            if (get.all_finishes.size == 0){
                binding.tvHighestFinish.text = "-"
            } else {
                binding.tvHighestFinish.text = get.all_finishes.max().toString()
            }
            if (get.throws_to_win.size == 0){
                binding.tvLessDarts.text = "-"
            } else {
                binding.tvLessDarts.text = get.throws_to_win.min().toString()
            }

            var cop = arrayListOf<Int>()
            val numbersByElement = get.all_three_throws_points.groupingBy { it }.eachCount()
            val most_throw = numbersByElement.maxBy { it.value }?.key
            val most_throw_amount = numbersByElement.get(most_throw)
            for (item in get.all_three_throws_points){
                if (item != most_throw) cop.add(item)
            }
            if ( cop.size != 0){
                val numbersByElement = cop.groupingBy { it }.eachCount()
                val second_most_throw = numbersByElement.maxBy { it.value }?.key
                val second_most_throw_amount = numbersByElement.get(second_most_throw)
                binding.tvMostThrows.text = most_throw.toString()+ "(" + most_throw_amount.toString() + ")"  + "\n" + second_most_throw.toString()
                var cop2 = arrayListOf<Int>()
                for (item in cop){
                    if (item != second_most_throw) cop2.add(item)
                }
                if ( cop2.size != 0) {
                    val numbersByElement = cop2.groupingBy { it }.eachCount()
                    val third_most_throw = numbersByElement.maxBy { it.value }?.key
                    val third_most_throw_amount = numbersByElement.get(third_most_throw)
                    binding.tvMostThrows.text =
                        most_throw.toString()+ " (" + most_throw_amount.toString() + ")"  + "\n" + second_most_throw.toString() + " (" + second_most_throw_amount.toString() + ")" + "\n" + third_most_throw.toString() + " " +
                                "(" + third_most_throw_amount.toString() + ")"
                } else {
                    binding.tvMostThrows.text =
                        most_throw.toString()+ " (" + most_throw_amount.toString() + ")"  + "\n" + second_most_throw.toString() + " (" + second_most_throw_amount.toString() + ")" + "\n" + "-"
                }
            } else {
                binding.tvMostThrows.text = most_throw.toString()+ " (" + most_throw_amount.toString() + ")"  + "\n" + "-" + "\n" + "-"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatusHolder {
        val binding = CountingStatisticsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerStatusHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerStatusHolder, position: Int) {
        if (position % 2 == 0){
            holder.bind(datalist[position], true, grey)
        } else {
            holder.bind(datalist[position], false, grey)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}