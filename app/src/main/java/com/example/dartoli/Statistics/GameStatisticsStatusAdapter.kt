package com.example.dartoli.Statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.Cricket.CricketPlayer
import com.example.dartoli.R
import com.example.dartoli.databinding.ListItemBinding
import com.example.dartoli.databinding.StatisticsGameItemBinding

class GameStatisticsStatusAdapter(var datalist:ArrayList<GameStatisticItem>, var color: Int): RecyclerView.Adapter<GameStatisticsStatusAdapter.GameStatusHolder>() {

    var grey = color
    class GameStatusHolder(val binding: StatisticsGameItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: GameStatisticItem, even: Boolean, grey: Int) {
            if (!even) binding.card.setBackgroundColor(grey)
            binding.tvGameName.text = get.game_name
            binding.tvPlayer1Name.text = get.player1_name
            binding.tvPlayer2Name.text = get.player2_name
            binding.tvPlayer1Wins.text = get.player1_wins.toString()
            binding.tvPlayer1Sets.text = get.player1_sets.toString()
            binding.tvPlayer1Legs.text = get.player1_legs.toString()
            binding.tvPlayer1ActualStreet.text = get.player1_actual_street.toString()
            binding.tvPlayer1HighestStreet.text = get.player1_longest_win_street.toString()
            binding.tvPlayer1ActualStreet.text = get.player1_actual_street.toString()
            binding.tvPlayer2Wins.text = get.player2_wins.toString()
            binding.tvPlayer2Sets.text = get.player2_sets.toString()
            binding.tvPlayer2Legs.text = get.player2_legs.toString()
            binding.tvPlayer2ActualStreet.text = get.player2_actual_street.toString()
            binding.tvPlayer2HighestStreet.text = get.player2_longest_win_street.toString()
            binding.tvPlayer2ActualStreet.text = get.player2_actual_street.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameStatusHolder {
        val binding = StatisticsGameItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GameStatusHolder(binding)
    }

    override fun onBindViewHolder(holder: GameStatusHolder, position: Int) {
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