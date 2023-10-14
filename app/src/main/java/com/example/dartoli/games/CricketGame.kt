package com.example.dartoli.games

import android.os.Parcel
import android.os.Parcelable
import com.example.dartoli.model.Player
import java.io.Serializable

class CricketGame(legs: Int, sets: Int, players: ArrayList<Player>): Serializable {
    var fifteens = arrayListOf<Int>()
    var sixteens = arrayListOf<Int>()
    var seventeens = arrayListOf<Int>()
    var eightteens = arrayListOf<Int>()
    var nineteens = arrayListOf<Int>()
    var twenties = arrayListOf<Int>()
    var bulls = arrayListOf<Int>()
    var points = arrayListOf<Int>()
    var amountOfPlayers = players.size
    var actualPlayerNumber = 0
    var actualDartsLeft = 3
    var actualRound = 1
    var game_players = players


    init {
        for (number in 1..players.size){
            fifteens.add(0)
            sixteens.add(0)
            seventeens.add(0)
            eightteens.add(0)
            nineteens.add(0)
            twenties.add(0)
            bulls.add(0)
            points.add(0)}
        }


    fun thrown_values(value: Int, amount: Int){
            when(value){
                15 -> add_throw(fifteens, amount)
                16 -> add_throw(sixteens, amount)
                17 -> add_throw(seventeens, amount)
                18 -> add_throw(eightteens, amount)
                19 -> add_throw(nineteens, amount)
                20 -> add_throw(twenties, amount)
                25 -> add_throw(bulls, amount)
                else -> finish_player_move()
            }
        finish_player_move()
    }

    fun add_throw(number_list: ArrayList<Int>, amount: Int){
        for (i in 1..amount){
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