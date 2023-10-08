package com.example.dartoli.data

import com.example.dartoli.model.Game
import com.example.dartoli.model.Player

class Datasource {
    fun loadGames(): List<Game> {
        return listOf<Game>(
            Game(3, "Cricket", "blablablub"),
            Game(4, "Cricket2", "blablablub2"),
            Game(5, "Cricket3", "blablablub3"))
    }

    fun loadPlayers(): List<Player> {
        return listOf<Player>(
            Player("Franko1"),
            Player("Franko2"),
            Player("Franko3"))
    }
}