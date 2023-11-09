package com.example.dartoli.Cricket

import java.io.Serializable

data class CricketPlayer(
    var playerName: String,
    var points: Int,
    var fifteens: Int,
    var sixteens: Int,
    var seventeens: Int,
    var eightteens: Int,
    var nineteens: Int,
    var twenties: Int,
    var bulls: Int,
    var fifteensClosed: Boolean,
    var sixteensClosed: Boolean,
    var seventeensClosed: Boolean,
    var eightteensClosed: Boolean,
    var nineteensClosed: Boolean,
    var twentiesClosed: Boolean,
    var bullsClosed: Boolean,
    var won_legs : Int,
    var needed_legs : Int,
    var won_sets: Int,
    var needed_sets: Int,
    var rank: Int,
    var overall_won_legs: Int
): Serializable
