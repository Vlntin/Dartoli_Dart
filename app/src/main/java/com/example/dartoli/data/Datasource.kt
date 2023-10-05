package com.example.dartoli.data

import com.example.dartoli.model.Game

class Datasource {
    fun loadGames(): List<Game> {
        return listOf<Game>(
            Game(3, "Cricket", "blablablub"),
            Game(4, "Cricket2", "blablablub2"),
            Game(5, "Cricket3", "blablablub3"))
    }
}