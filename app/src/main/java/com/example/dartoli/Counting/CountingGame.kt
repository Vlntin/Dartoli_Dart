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
                var actual_player = game_players[actualPlayerNumber]
                if (score_allowed(score, amount)){
                        actual_player.thrown_darts++
                        actual_player.average = (actual_player.average * (actual_player.thrown_darts - 1) + score) / actual_player.thrown_darts
                        actual_player.points = actual_player.points - score
                        actual_player.throws_points.add(score)
                        finish_player_move()
                        if (check_leg_finished()){
                                if (check_set_finished()){
                                        if (check_game_finished()) return true
                                }
                        }
                        return false
                } else {
                        while (actualDartsLeft > 0){
                                actual_player.throws_points.add(0)
                                actual_player.thrown_darts++
                                actual_player.average = actual_player.average * (actual_player.thrown_darts - 1) / actual_player.thrown_darts
                                actualDartsLeft--
                        }
                        actual_player.points = actual_player.points + actual_player.throws_points[actual_player.throws_points.size -1] + actual_player.throws_points[actual_player.throws_points.size -2] + actual_player.throws_points[actual_player.throws_points.size -3]
                        actual_player.average = ((501 - actual_player.points) / actual_player.thrown_darts).toFloat()
                        change_actual_player()
                        return false

                }
        }

        fun score_allowed(score: Int, amount: Int): Boolean{
                if (game_players[actualPlayerNumber].points - score != 1 &&
                        game_players[actualPlayerNumber].points > score ||
                        game_players[actualPlayerNumber].points == score && amount == 2){
                        return true
                } else {
                        return false
                }
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
                player.points = 501
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

        fun ckeck_potential_double_hit(): Boolean {
                var player: CountingPlayer
                if (actualDartsLeft == 3) {
                        if (actualPlayerNumber == 0){
                                player = game_players[game_players.size -1]
                        }else{
                                player = game_players[actualPlayerNumber - 1]
                        }
                   if (player.points <= 50){
                           return true
                   } else {
                           return false
                   }
                } else {
                        return false
                }
        }

        fun throws_on_double(amount: Int) {
                if (actualPlayerNumber == 0){
                        game_players[game_players.size -1].throws_on_doubles = game_players[game_players.size -1].throws_on_doubles + amount
                }else{
                        game_players[actualPlayerNumber - 1].throws_on_doubles = game_players[actualPlayerNumber - 1].throws_on_doubles + amount
                }
        }
}