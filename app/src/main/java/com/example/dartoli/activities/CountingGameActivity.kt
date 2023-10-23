package com.example.dartoli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dartoli.R
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCountingGameBinding
import com.example.dartoli.databinding.ActivityCricketBinding
import com.example.dartoli.games.CricketGame
import com.example.dartoli.model.CricketPlayer

class CountingGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountingGameBinding
    private var playingPlayers = arrayListOf<CountingPlayer>()
    private lateinit var game: CricketGame
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var player_id_array = intent.extras?.getIntArray("players")
        var legs = intent.getIntExtra("legs", 0)
        var sets = intent.getIntExtra("sets", 0)
        val myDB = PlayerDatabaseHandler(this)
        val players_dataset = myDB.readAllPlayers()
        for (counter in 0..player_id_array!!.size - 1){
            for (player in players_dataset){
                if(player.id == player_id_array[counter]){
                    playingPlayers.add(CricketPlayer(player.playerName, 0, 0,0,0,0,0,0,0, false, false, false, false, false, false, false, 0, legs, 0, sets, 0))
                }
            }
        }
        game = CricketGame(legs, sets, playingPlayers)
    }
}