package com.example.dartoli.Counting

data class CountingPlayer(
    var playerName: String,
    var points: Int,
    var thrown_darts: Int,
    var average: Float,
    var won_legs : Int,
    var needed_legs : Int,
    var won_sets: Int,
    var needed_sets: Int,
    var rank: Int,)
