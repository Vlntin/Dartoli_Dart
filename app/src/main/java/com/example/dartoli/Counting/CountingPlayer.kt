package com.example.dartoli.Counting

data class CountingPlayer(
    var playerName: String,
    var points: Int,
    var leg_thrown_darts: Int,
    var leg_average: Double,
    var won_legs : Int,
    var needed_legs : Int,
    var won_sets: Int,
    var needed_sets: Int,
    var throws_on_doubles: Int,
    var rank: Int,
    var throws_points: ArrayList<Int>,
    var double_throws_in_round: Int,
    var hit_doubles: Int,
    var game_average: Double,
    var all_averages: ArrayList<Double>,
    var all_thrown_darts: ArrayList<Int>)
