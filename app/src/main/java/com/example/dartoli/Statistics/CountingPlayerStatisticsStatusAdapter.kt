package com.example.dartoli.Statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.databinding.CountingPlayerStatisticsItemBinding

class CountingPlayerStatisticsStatusAdapter(var datalist:ArrayList<CountingPlayerStatisticsItem>, var color: Int): RecyclerView.Adapter<CountingPlayerStatisticsStatusAdapter.PlayerStatusHolder>() {

    var grey = color
    class PlayerStatusHolder(val binding: CountingPlayerStatisticsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CountingPlayerStatisticsItem, even: Boolean, grey: Int) {
            if (!even) binding.card.setBackgroundColor(grey)
            binding.tvPlayername.text = get.player_name
            binding.tvGameAmount.text = get.games.toString()
            binding.tvWins.text = get.wins.toString()
            binding.tvLongestStreet.text = get.longest_street.toString()
            binding.tvActualStreet.text = get.actual_street.toString()
            binding.tvMostHits.text = get.most_hits
            binding.tvDoubleQuote.text = get.double_quote
            if (get.games > 0) binding.tvAverage.text = String.format("%.2f", get.average) else binding.tvAverage. text = "-"
            if (get.wins > 0){
                binding.tvBestAverage.text = String.format("%.2f", get.best_average)
                binding.tvBadestAverage.text = String.format("%.2f", get.badest_average)
            } else {
                binding.tvBestAverage. text = "-"
                binding.tvBadestAverage. text = "-"
            }

            if (get.highest_finish != null) {
                binding.tvHighestFinish.text = get.highest_finish.toString()
                binding.tvLowestFinish.text = get.lowest_finish.toString()
            } else {
                binding.tvHighestFinish.text = "-"
                binding.tvLowestFinish.text = "-"
            }

            if (get.less_darts != null){
                binding.tvLessDarts.text = get.less_darts.toString()
                binding.tvMostDarts.text = get.most_darts.toString()
                binding.tvAverageDarts.text = String.format("%.2f", get.average_darts)
            } else {
                binding.tvLessDarts.text = "-"
                binding.tvMostDarts.text = "-"
                binding.tvAverageDarts.text = "-"
            }
            if (get.most_double != null) binding.tvMostDouble.text = get.most_double else binding.tvMostDouble.text = "-"

            binding.tvIntervals.text = "  0+ :  ${get.intervals[0]}" + "x" +
                    "\n 20+ :  ${get.intervals[1]}" + "x" +
                    "\n 40+ :  ${get.intervals[2]}" + "x" +
                    "\n 60+ :  ${get.intervals[3]}" + "x" +
                    "\n100+ :  ${get.intervals[4]}" + "x" +
                    "\n120+ :  ${get.intervals[5]}" + "x" +
                    "\n 180 :  ${get.intervals[6]}" + "x"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatusHolder {
        val binding = CountingPlayerStatisticsItemBinding
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