package com.example.dartoli.games

import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.example.dartoli.activities.CricketActivity
import com.example.dartoli.model.CricketPlayer
import com.example.dartoli.model.Player
import java.io.Serializable

class CricketGame(legs: Int, sets: Int, players: ArrayList<CricketPlayer>): Serializable {
    var actualPlayerNumber = 0
    var actualDartsLeft = 3
    var actualRound = 1
    var game_players = players


    fun thrown_values(value: Int, amount: Int){
        for (i in 1..amount){
            if (value == 15) {
                if (game_players[actualPlayerNumber].fifteens == 3 && !game_players[actualPlayerNumber].fifteensClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 15
                }
                if (game_players[actualPlayerNumber].fifteens == 2 ) {
                    game_players[actualPlayerNumber].fifteens = 3
                    var closed = true
                    for (element in game_players){
                        if (element.fifteens < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.fifteensClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].fifteens < 2 ) {
                        game_players[actualPlayerNumber].fifteens++
                }

            }
            if (value == 16) {
                if (game_players[actualPlayerNumber].sixteens == 3 && !game_players[actualPlayerNumber].sixteensClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 16
                }
                if (game_players[actualPlayerNumber].sixteens == 2 ) {
                    game_players[actualPlayerNumber].sixteens = 3
                    var closed = true
                    for (element in game_players){
                        if (element.sixteens < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.sixteensClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].sixteens < 2 ) {
                    game_players[actualPlayerNumber].sixteens++
                }
            }
            if (value == 17) {
                if (game_players[actualPlayerNumber].seventeens == 3 && !game_players[actualPlayerNumber].seventeensClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 17
                }
                if (game_players[actualPlayerNumber].seventeens == 2 ) {
                    game_players[actualPlayerNumber].seventeens++
                    var closed = true
                    for (element in game_players){
                        if (element.seventeens < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.seventeensClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].seventeens < 2 ) {
                    game_players[actualPlayerNumber].seventeens++
                }
            }
            if (value == 18) {
                if (game_players[actualPlayerNumber].eightteens == 3 && !game_players[actualPlayerNumber].eightteensClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 18
                }
                if (game_players[actualPlayerNumber].eightteens == 2 ) {
                    game_players[actualPlayerNumber].eightteens++
                    var closed = true
                    for (element in game_players){
                        if (element.eightteens < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.eightteensClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].eightteens < 2 ) {
                    game_players[actualPlayerNumber].eightteens++
                }
            }
            if (value == 19) {
                if (game_players[actualPlayerNumber].nineteens == 3 && !game_players[actualPlayerNumber].nineteensClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 19
                }
                if (game_players[actualPlayerNumber].nineteens == 2 ) {
                    game_players[actualPlayerNumber].nineteens++
                    var closed = true
                    for (element in game_players){
                        if (element.nineteens < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.nineteensClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].nineteens < 2 ) {
                    game_players[actualPlayerNumber].nineteens++
                }
            }
            if (value == 20) {
                if (game_players[actualPlayerNumber].twenties == 3 && !game_players[actualPlayerNumber].twentiesClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 20
                }
                if (game_players[actualPlayerNumber].twenties == 2 ) {
                    game_players[actualPlayerNumber].twenties++
                    var closed = true
                    for (element in game_players){
                        if (element.twenties < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.twentiesClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].twenties < 2 ) {
                    game_players[actualPlayerNumber].twenties++
                }
            }
            if (value == 25) {
                if (game_players[actualPlayerNumber].bulls == 3 && !game_players[actualPlayerNumber].bullsClosed) {
                    game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points + 25
                }
                if (game_players[actualPlayerNumber].bulls == 2 ) {
                    game_players[actualPlayerNumber].bulls++
                    var closed = true
                    for (element in game_players){
                        if (element.bulls < 3) closed = false
                    }
                    if (closed) {
                        for (element in game_players){
                            element.bullsClosed = true
                        }
                    }
                }
                if (game_players[actualPlayerNumber].bulls < 2 ) {
                    game_players[actualPlayerNumber].bulls++
                }
            }
        }
        finish_player_move()
    }

    fun finish_player_move(){
        if (actualDartsLeft == 1){
            change_actual_player()
        } else {
            actualDartsLeft--
        }
    }

    fun change_actual_player() {
        actualDartsLeft = 3
        if (actualPlayerNumber == game_players.size - 1){
            actualPlayerNumber = 0
            actualRound++
        } else {
            actualPlayerNumber++
        }
    }

}