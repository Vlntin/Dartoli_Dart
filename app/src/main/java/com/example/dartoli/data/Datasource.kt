package com.example.dartoli.data

import com.example.dartoli.model.Game
import com.example.dartoli.model.Player

class Datasource {
    fun loadGames(): List<Game> {
        return listOf<Game>(
            Game(3, "Cricket", "blablablub", true),
            Game(4, "Cricket2", "blablablub2", false),
            Game(5, "Cricket3", "blablablub3", false)
        )
    }
}

