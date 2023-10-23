package com.example.dartoli.Counting


import com.example.dartoli.Cricket.CricketPlayer
import com.example.dartoli.GameModel
import java.io.Serializable

class CountingGame(legs: Int, sets: Int, players: ArrayList<CountingPlayer>): Serializable, GameModel() {
        val needed_legs = legs
        val needed_sets = sets
        var actualPlayerNumber = 0
        var actualDartsLeft = 3
        var actualRound = 1
        var game_players = players

        fun thrown_values(value: Int, amount: Int): Boolean{
                var score = value * amount
                game_players[actualPlayerNumber].points = game_players[actualPlayerNumber].points - score
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
                for (player in game_players){
                        if (player.points == 0){
                                player.won_legs++
                                reset_for_new_leg()
                                return true
                        }
                }
                return false
        }

        fun reset_for_new_leg(){
                actualDartsLeft = 3
                actualRound = 1
                actualPlayerNumber = 0
                for (player in game_players){
                        reset_player(player)
                }
        }

        fun reset_player(player: CountingPlayer){
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