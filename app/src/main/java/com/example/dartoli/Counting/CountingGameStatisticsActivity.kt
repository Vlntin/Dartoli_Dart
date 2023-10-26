package com.example.dartoli.Counting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.databinding.ActivityCountingGameStatisticsBinding

private lateinit var binding: ActivityCountingGameStatisticsBinding

private lateinit var playerAdapter: CountingPlayerStatisticsAdapter
private lateinit var rvPlayerStatistics: RecyclerView
private var playingPlayers = arrayListOf<CountingPlayer>()

class CountingGameStatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingGameStatisticsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        var game = intent.getSerializableExtra("a") as CountingGame?
        playingPlayers = game!!.game_players

        rvPlayerStatistics = binding.rvRecycler
        setupPlayerStatusRecyclerView()

    }

    private fun setupPlayerStatusRecyclerView() {
        rvPlayerStatistics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        playerAdapter = CountingPlayerStatisticsAdapter(playingPlayers!!, ContextCompat.getColor(this, R.color.cricket_background_color))
        rvPlayerStatistics.adapter = playerAdapter
    }

    private fun updateAdapter() {
        for (i in 0..playingPlayers.size-1){
            playerAdapter.notifyItemChanged(i)
        }
    }
}