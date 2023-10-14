package com.example.dartoli.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.adapter.PlayerStatusAdapter
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCricketBinding
import com.example.dartoli.games.CricketGame
import com.example.dartoli.model.Player

class CricketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCricketBinding
    private var amount = 1

    //private lateinit var singleButton: TextView
    //private lateinit var doubleButton: TextView
    //private lateinit var tribleButton: TextView

    private lateinit var playerAdapter: PlayerStatusAdapter
    private lateinit var rvPlayerStatus: RecyclerView


    private var playingPlayers = arrayListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCricketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //val game = intent.getSerializableExtra("cricket_game_key" )
        var player_id_array = intent.extras?.getIntArray("players")
        var legs = intent.getIntExtra("legs", 0)
        var sets = intent.getIntExtra("sets", 0)
        val myDB = PlayerDatabaseHandler(this)
        val players_dataset = myDB.readAllPlayers()
        for (counter in 0..player_id_array!!.size - 1){
            for (player in players_dataset){
                if(player.id == player_id_array[counter]){
                    playingPlayers.add(player)
                }
            }
        }
        val game = CricketGame(legs, sets, playingPlayers)

/**
        singleButton = binding.singleBtn
        doubleButton = binding.doubleBtn
        tribleButton = binding.tripleBtn

        singleButton.setOnClickListener(){
            singleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            doubleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            tribleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            amount = 1
        }

        doubleButton.setOnClickListener(){
            doubleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            singleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            tribleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            amount = 2
        }

        tribleButton.setOnClickListener(){
            tribleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            doubleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            singleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            amount = 3
        }


        binding.fifteenBtn.setOnClickListener(){
            game.thrown_values(15, amount)
        }
        binding.sixteenBtn.setOnClickListener(){
            game.thrown_values(16, amount)
        }
        binding.seventeenBtn.setOnClickListener(){
            game.thrown_values(17, amount)
        }
        binding.eightteenBtn.setOnClickListener(){
            game.thrown_values(18, amount)
        }
        binding.nineteenBtn.setOnClickListener(){
            game.thrown_values(19, amount)
        }
        binding.twentyBtn.setOnClickListener(){
            game.thrown_values(20, amount)
        }
        binding.bullBtn.setOnClickListener(){
            game.thrown_values(25, amount)
            Toast.makeText(this, "bull", Toast.LENGTH_LONG).show()
        }*/

        rvPlayerStatus = binding.rvRecycler
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        playerAdapter = PlayerStatusAdapter(playingPlayers!!)
        rvPlayerStatus.adapter = playerAdapter


    }


    private fun setupPlayerStatusRecyclerView() {
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        playerAdapter = PlayerStatusAdapter(playingPlayers!!)
        rvPlayerStatus.adapter = playerAdapter
    }
}