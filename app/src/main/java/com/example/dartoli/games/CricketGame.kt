package com.example.dartoli.games

import com.example.dartoli.model.Player

class CricketGame(legs: Int, sets: Int, players: ArrayList<Player>) {
    lateinit var fifteens: ArrayList<Int>
    lateinit var sixteens: ArrayList<Int>
    lateinit var seventeens: ArrayList<Int>
    lateinit var eightteens: ArrayList<Int>
    lateinit var nineteens: ArrayList<Int>
    lateinit var twenties: ArrayList<Int>
    lateinit var bulls: ArrayList<Int>
    lateinit var points: ArrayList<Int>
    var amountOfPlayers = players.size
    var actualPlayerNumber = 0
    var actualDartsLeft = 3
    var actualRound = 1

    init {
        for (number in 1..players.size){
            fifteens.add(0)
            sixteens.add(0)
            seventeens.add(0)
            eightteens.add(0)
            nineteens.add(0)
            twenties.add(0)
            bulls.add(0)
            points.add(0)
        }
    }

}