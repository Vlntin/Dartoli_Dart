package com.example.dartoli.model

import java.sql.SQLData

data class Match(
    val id: Int,
    val game_id: Int,
    val date: String,
    val player_ids: ArrayList<Int>,
    val won_legs: ArrayList<Int>,
    val won_sets: ArrayList<Int>
)
