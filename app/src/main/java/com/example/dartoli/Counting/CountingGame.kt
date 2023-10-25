package com.example.dartoli.Counting


import android.util.Log
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

        fun thrown_values(value: Int, amount: Int){
                var score = value * amount
                var actual_player = game_players[actualPlayerNumber]
                set_double_tries(actual_player)
                if (score_allowed(score, amount)){
                        actual_player.leg_thrown_darts++
                        actual_player.points = actual_player.points - score
                        actual_player.throws_points.add(score)
                } else {
                        actual_player.throws_points.add(0)
                        actual_player.leg_thrown_darts++
                        while (actualDartsLeft > 1){
                                actual_player.throws_points.add(0)
                                actual_player.leg_thrown_darts++
                                actualDartsLeft--
                        }
                        actual_player.points = actual_player.points + actual_player.throws_points[actual_player.throws_points.size -1] + actual_player.throws_points[actual_player.throws_points.size -2] + actual_player.throws_points[actual_player.throws_points.size -3]
                }
        }

        fun score_allowed(score: Int, amount: Int): Boolean{
                if ((game_players[actualPlayerNumber].points - score != 1 &&
                        game_players[actualPlayerNumber].points > score) ||
                        (game_players[actualPlayerNumber].points == score && amount == 2)){
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
                                player.hit_doubles++
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
                player.all_averages.add(player.leg_average)
                player.all_thrown_darts.add(player.leg_thrown_darts)
                player.leg_average = 0.0
                player.leg_thrown_darts = 0
                player.double_throws_in_round = 0
        }
        fun finish_player_move(){
                if (actualDartsLeft == 1){
                        var actual_player = game_players[actualPlayerNumber]
                        actual_player.leg_average =(((501 - actual_player.points) / actual_player.leg_thrown_darts) * 3).toDouble()
                        if (actual_player.all_averages.size == 0){
                                actual_player.game_average = actual_player.leg_average
                        } else{
                                var summed_throws : Double
                                summed_throws = 0.0
                                var past_average : Double
                                past_average = 0.0
                                for (throws in actual_player.all_thrown_darts){
                                        summed_throws = summed_throws + throws
                                }
                                for (i in 0..actual_player.all_thrown_darts.size - 1){
                                        past_average = past_average + actual_player.all_averages[i] * (actual_player.all_thrown_darts[i] / summed_throws)
                                }
                                actual_player.game_average = (past_average * (summed_throws / ( summed_throws + actual_player.leg_thrown_darts))) + (actual_player.leg_average * (actual_player.leg_thrown_darts.toDouble() / ( summed_throws + actual_player.leg_thrown_darts)))
                        }

                        change_actual_player()
                } else {
                        actualDartsLeft--
                }
        }

        fun change_actual_player() {
                actualDartsLeft = 3
                game_players[actualPlayerNumber].double_throws_in_round = 0
                if (actualPlayerNumber == game_players.size - 1){
                        actualPlayerNumber = 0
                        actualRound++
                } else {
                        actualPlayerNumber++
                }
        }

        fun check_game_state(): Boolean {
                if (check_leg_finished()){
                        if (check_set_finished()){
                                if (check_game_finished()) return true
                        }
                }
                return false
        }
        fun check_potential_double_hit(): Int {
                var player = game_players[actualPlayerNumber]
                if (actualDartsLeft == 1 || player.points == 0) {
                        return player.double_throws_in_round
                } else {
                        return 0
                }
        }

        fun set_double_tries(player: CountingPlayer){
                if (player.points == 50 ||
                        player.points <= 40 && player.points % 2 == 0){
                        player.double_throws_in_round++
                }
        }

        fun throws_on_double(amount: Int) {
                game_players[actualPlayerNumber].throws_on_doubles = game_players[actualPlayerNumber].throws_on_doubles + amount
        }

        fun has_player_finished(): Boolean {
                if (game_players[actualPlayerNumber].points == 0){
                        return true
                } else {
                        return false
                }
        }
}