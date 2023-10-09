package com.example.dartoli.model

data class Game(
    val maxPlayerNumber: Int,
    val titel: String,
    val description: String,
    var selected: Boolean,
)
