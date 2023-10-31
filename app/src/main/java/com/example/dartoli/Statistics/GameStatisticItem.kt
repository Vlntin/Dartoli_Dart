package com.example.dartoli.Statistics

data class GameStatisticItem(
    var game_name: String,
    var player1_name: String,
    var player2_name: String,
    var player1_wins: Int,
    var player1_sets: Int,
    var player1_legs: Int,
    var player1_highest_win: String,
    var player1_longest_win_street: Int,
    var player1_actual_street: Int,
    var player2_wins: Int,
    var player2_sets: Int,
    var player2_legs: Int,
    var player2_highest_win: String,
    var player2_longest_win_street: Int,
    var player2_actual_street: Int,
)