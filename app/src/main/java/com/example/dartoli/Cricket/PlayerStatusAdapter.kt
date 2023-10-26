package com.example.dartoli.Cricket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.databinding.ListItemBinding

class PlayerStatusAdapter(var datalist:ArrayList<CricketPlayer>, var color: Int):RecyclerView.Adapter<PlayerStatusAdapter.PlayerStatusHolder>() {

    var grey = color
    class PlayerStatusHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: CricketPlayer, even: Boolean, grey: Int) {
            if (!even) binding.card.setBackgroundColor(grey)
            binding.tvPlayername.text = get.playerName
            binding.pointsLayout.text = get.points.toString()
            set_pictures(get, even)
        }
        fun set_pictures(player: CricketPlayer, even: Boolean){
            if(even){
                if(!player.fifteensClosed) binding.fifteenPointsLayout.setBackgroundResource(R.color.white)
                if(!player.sixteensClosed) binding.sixteenPointsLayout.setBackgroundResource(R.color.white)
                if(!player.seventeensClosed) binding.seventeenPointsLayout.setBackgroundResource(R.color.white)
                if(!player.eightteensClosed) binding.eightteenPointsLayout.setBackgroundResource(R.color.white)
                if(!player.nineteensClosed) binding.nineteenPointsLayout.setBackgroundResource(R.color.white)
                if(!player.twentiesClosed) binding.twentyPointsLayout.setBackgroundResource(R.color.white)
                if(!player.bullsClosed) binding.bullPointsLayout.setBackgroundResource(R.color.white)
            } else {
                if(!player.fifteensClosed) binding.fifteenPointsLayout.setBackgroundResource(R.color.cricket_background_color)
                if(!player.sixteensClosed) binding.sixteenPointsLayout.setBackgroundResource(R.color.cricket_background_color)
                if(!player.seventeensClosed) binding.seventeenPointsLayout.setBackgroundResource(R.color.cricket_background_color)
                if(!player.eightteensClosed) binding.eightteenPointsLayout.setBackgroundResource(R.color.cricket_background_color)
                if(!player.nineteensClosed) binding.nineteenPointsLayout.setBackgroundResource(R.color.cricket_background_color)
                if(!player.twentiesClosed) binding.twentyPointsLayout.setBackgroundResource(R.color.cricket_background_color)
                if(!player.bullsClosed) binding.bullPointsLayout.setBackgroundResource(R.color.cricket_background_color)
            }

            if (player.fifteens == 0) {
                binding.iv151.setImageResource(R.drawable.outlined_circle)
                binding.iv152.setImageResource(R.drawable.outlined_circle)
                binding.iv153.setImageResource(R.drawable.outlined_circle)
            }
            if (player.fifteens > 0) binding.iv151.setImageResource(R.drawable.outlined_circle_checked)
            if (player.fifteens > 1) binding.iv152.setImageResource(R.drawable.outlined_circle_checked)
            if (player.fifteens > 2) {
                binding.iv153.setImageResource(R.drawable.outlined_circle_checked)
                binding.fifteenPointsLayout.setBackgroundResource(R.color.green)
            }
            if (player.sixteens == 0) {
                binding.iv161.setImageResource(R.drawable.outlined_circle)
                binding.iv162.setImageResource(R.drawable.outlined_circle)
                binding.iv163.setImageResource(R.drawable.outlined_circle)
            }
            if (player.sixteens > 0) binding.iv161.setImageResource(R.drawable.outlined_circle_checked)
            if (player.sixteens > 1) binding.iv162.setImageResource(R.drawable.outlined_circle_checked)
            if (player.sixteens > 2) {
                binding.iv163.setImageResource(R.drawable.outlined_circle_checked)
                binding.sixteenPointsLayout.setBackgroundResource(R.color.green)
            }
            if (player.eightteens == 0) {
                binding.iv181.setImageResource(R.drawable.outlined_circle)
                binding.iv182.setImageResource(R.drawable.outlined_circle)
                binding.iv183.setImageResource(R.drawable.outlined_circle)
            }
            if (player.eightteens > 0) binding.iv181.setImageResource(R.drawable.outlined_circle_checked)
            if (player.eightteens > 1) binding.iv182.setImageResource(R.drawable.outlined_circle_checked)
            if (player.eightteens > 2) {
                binding.iv183.setImageResource(R.drawable.outlined_circle_checked)
                binding.eightteenPointsLayout.setBackgroundResource(R.color.green)
            }
            if (player.nineteens == 0) {
                binding.iv191.setImageResource(R.drawable.outlined_circle)
                binding.iv192.setImageResource(R.drawable.outlined_circle)
                binding.iv193.setImageResource(R.drawable.outlined_circle)
            }
            if (player.nineteens > 0) binding.iv191.setImageResource(R.drawable.outlined_circle_checked)
            if (player.nineteens > 1) binding.iv192.setImageResource(R.drawable.outlined_circle_checked)
            if (player.nineteens > 2) {
                binding.iv193.setImageResource(R.drawable.outlined_circle_checked)
                binding.nineteenPointsLayout.setBackgroundResource(R.color.green)
            }
            if (player.seventeens == 0) {
                binding.iv171.setImageResource(R.drawable.outlined_circle)
                binding.iv172.setImageResource(R.drawable.outlined_circle)
                binding.iv173.setImageResource(R.drawable.outlined_circle)
            }
            if (player.seventeens > 0) binding.iv171.setImageResource(R.drawable.outlined_circle_checked)
            if (player.seventeens > 1) binding.iv172.setImageResource(R.drawable.outlined_circle_checked)
            if (player.seventeens > 2) {
                binding.iv173.setImageResource(R.drawable.outlined_circle_checked)
                binding.seventeenPointsLayout.setBackgroundResource(R.color.green)
            }
            if (player.twenties == 0) {
                binding.iv201.setImageResource(R.drawable.outlined_circle)
                binding.iv202.setImageResource(R.drawable.outlined_circle)
                binding.iv203.setImageResource(R.drawable.outlined_circle)
            }
            if (player.twenties > 0) binding.iv201.setImageResource(R.drawable.outlined_circle_checked)
            if (player.twenties > 1) binding.iv202.setImageResource(R.drawable.outlined_circle_checked)
            if (player.twenties > 2) {
                binding.iv203.setImageResource(R.drawable.outlined_circle_checked)
                binding.twentyPointsLayout.setBackgroundResource(R.color.green)
            }
            if (player.bulls == 0) {
                binding.ivBull1.setImageResource(R.drawable.outlined_circle)
                binding.ivBull2.setImageResource(R.drawable.outlined_circle)
                binding.ivBull3.setImageResource(R.drawable.outlined_circle)
            }
            if (player.bulls > 0) binding.ivBull1.setImageResource(R.drawable.outlined_circle_checked)
            if (player.bulls > 1) binding.ivBull2.setImageResource(R.drawable.outlined_circle_checked)
            if (player.bulls > 2) {
                binding.ivBull3.setImageResource(R.drawable.outlined_circle_checked)
                binding.bullPointsLayout.setBackgroundResource(R.color.green)
            }
            if(player.fifteensClosed) binding.fifteenPointsLayout.setBackgroundResource(R.color.red)
            if(player.sixteensClosed) binding.sixteenPointsLayout.setBackgroundResource(R.color.red)
            if(player.seventeensClosed) binding.seventeenPointsLayout.setBackgroundResource(R.color.red)
            if(player.eightteensClosed) binding.eightteenPointsLayout.setBackgroundResource(R.color.red)
            if(player.nineteensClosed) binding.nineteenPointsLayout.setBackgroundResource(R.color.red)
            if(player.twentiesClosed) binding.twentyPointsLayout.setBackgroundResource(R.color.red)
            if(player.bullsClosed) binding.bullPointsLayout.setBackgroundResource(R.color.red)

            binding.tvLegs.setText(player.won_legs.toString() + "/" + player.needed_legs)
            binding.tvSets.setText(player.won_sets.toString() + "/" + player.needed_sets)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatusHolder {
        val binding = ListItemBinding
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