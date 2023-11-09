package com.example.dartoli.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dartoli.Cricket.CricketGame
import com.example.dartoli.data.CountingPlayerDatabaseHandler
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.MatchesDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityMainBinding
import com.example.dartoli.model.Game

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnMultiplayer.setOnClickListener {
            startActivity(Intent(this@MainActivity, MultiplayerActivity::class.java))
            finish()
        }
        binding.btnStatistics.setOnClickListener {
            startActivity(Intent(this@MainActivity, CountingPlayerStatisticsActivity::class.java))
            finish()
        }
    }
}