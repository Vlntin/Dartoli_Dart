package com.example.dartoli.model

data class CountingPlayerResults(
    var id: Int,
    var player_id : Int,
    var date: String,
    var rank: Int,
    var average: Double,
    var throw_amount: Int,
    var throws: ArrayList<Int>,
    var double_tries: Int,
    var double_hits_amount : Int,
    var double_hits: ArrayList<Int>,
    var finishes: ArrayList<Int>,
    var needed_darts_to_win: ArrayList<Int>
)
