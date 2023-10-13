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

    fun thrown_values(values: ArrayList<Int>){
        for(element in values){
            when(element){
                15 -> add_throw(fifteens)
                16 -> add_throw(sixteens)
                17 -> add_throw(seventeens)
                18 -> add_throw(eightteens)
                19 -> add_throw(nineteens)
                20 -> add_throw(twenties)
                25 -> add_throw(bulls)
                else -> finish_player_move()
            }
        }
        finish_player_move()
    }

    fun add_throw(number_list: ArrayList<Int>){
        if (number_list[actualPlayerNumber] < 3){
            number_list[actualPlayerNumber]++
        } else {
            for (counter in 0..number_list.size - 1) {
                if (number_list[counter] <3 && counter != actualPlayerNumber) {
                    add_points(number_list)
                    break
                }
            }
        }
    }

    fun add_points(number_list: ArrayList<Int>){
        when(number_list){
            fifteens -> points[actualPlayerNumber] = points[actualPlayerNumber] + 15
            sixteens -> points[actualPlayerNumber] = points[actualPlayerNumber] + 16
            seventeens -> points[actualPlayerNumber] = points[actualPlayerNumber] + 17
            eightteens -> points[actualPlayerNumber] = points[actualPlayerNumber] + 18
            nineteens -> points[actualPlayerNumber] = points[actualPlayerNumber] + 19
            twenties -> points[actualPlayerNumber] = points[actualPlayerNumber] + 20
            bulls -> points[actualPlayerNumber] = points[actualPlayerNumber] + 25
            else -> finish_player_move()
        }
    }

    fun finish_player_move(){
        if (actualDartsLeft == 1){
            change_actual_player()
        } else {
            actualDartsLeft--
        }
    }

    fun change_actual_player(){
        actualDartsLeft = 3
        if (actualPlayerNumber == points.size - 1){
            actualPlayerNumber = 0
            actualRound++
        } else {
            actualPlayerNumber++
        }
    }

}