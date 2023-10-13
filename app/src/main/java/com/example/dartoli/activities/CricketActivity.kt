package com.example.dartoli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dartoli.R
import com.example.dartoli.games.CricketGame

class CricketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cricket)
        val game: CricketGame
        game = getIntent().getSerializableExtra("game_key") as CricketGame
        Toast.makeText(this, game.actualRound.toString(), Toast.LENGTH_LONG).show()
    }
}