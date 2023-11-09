package com.example.dartoli.Statistics

data class CountingPlayerStatisticsItem(
    var player_name: String,
    var games: Int,
    var wins: Int,
    var longest_street: Int,
    var actual_street: Int,
    var average: Double?,
    var best_average: Double?,
    var badest_average: Double?,
    var double_quote: String,
    var most_double: String?,
    var highest_finish: Int?,
    var lowest_finish: Int?,
    var less_darts: Int?,
    var average_darts: Double?,
    var most_darts: Int?,
    var most_hits: String,
    var intervals: Array<Int>
)
