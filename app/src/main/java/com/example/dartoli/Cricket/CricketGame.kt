package com.example.dartoli.Cricket

import java.io.Serializable

class CricketGame(legs: Int, sets: Int, players: ArrayList<CricketPlayer>): Serializable {
    val needed_legs = legs
    val needed_sets = sets
    var actualPlayerNumber = 0
    var actualDartsLeft = 3
    var actualRound = 1
    var game_players = players
    var set_start_player = 0
    var leg_start_player = 0


    fun thrown_values(value: Int, amount: Int): Boolean{
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
        if (check_leg_finished()){
            if (check_set_finished()){
                if (check_game_finished()) return true
            }
        }
        return false
    }
    fun check_game_finished(): Boolean{
        for (player in game_players){
            if (player.won_sets == player.needed_sets) return true
        }
        return false
    }

    fun check_set_finished(): Boolean {
        for (player in game_players){
            if (player.won_legs == needed_legs){
                if (set_start_player < game_players.size - 1){
                    set_start_player++
                } else {
                    set_start_player = 0
                }
                actualPlayerNumber = set_start_player
                leg_start_player = set_start_player
                player.won_sets++
                for (player in game_players){
                    player.won_legs = 0
                }
                return true
            }
        }
        return false
    }

    fun check_leg_finished(): Boolean {
        var result = false
        for (i in 0..game_players.size -1){
            var best_player = game_players[i]
            if (best_player.fifteens == 3 &&
                best_player.sixteens == 3 &&
                best_player.seventeens == 3 &&
                best_player.eightteens == 3 &&
                best_player.nineteens == 3 &&
                best_player.twenties == 3 &&
                best_player.bulls == 3){
                var best = true
                for (player in game_players){
                    if (!player.equals(best_player) && player.points > best_player.points) best = false
                }
                if (best){
                    best_player.won_legs++
                    best_player.overall_won_legs++
                    reset_for_new_leg()
                    result = true
                }
            }
        }
        return result
    }

    fun reset_for_new_leg(){
        if (leg_start_player < game_players.size - 1){
            leg_start_player++

        } else {
            leg_start_player = 0
        }
        actualPlayerNumber = leg_start_player
        actualDartsLeft = 3
        actualRound = 1
        for (player in game_players){
            reset_player(player)
        }
    }

    fun reset_player(player: CricketPlayer){
        player.fifteens = 0
        player.sixteens = 0
        player.seventeens = 0
        player.eightteens = 0
        player.nineteens = 0
        player.twenties = 0
        player.bulls = 0
        player.fifteensClosed = false
        player.sixteensClosed = false
        player.seventeensClosed = false
        player.eightteensClosed = false
        player.nineteensClosed = false
        player.twentiesClosed = false
        player.bullsClosed = false
        player.points = 0
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