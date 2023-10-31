package com.example.dartoli.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.Cricket.PlayerStatusAdapter
import com.example.dartoli.R
import com.example.dartoli.Statistics.GameStatisticItem
import com.example.dartoli.Statistics.GameStatisticsStatusAdapter
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.MatchesDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityStatisticsBinding
import com.example.dartoli.model.Game
import com.example.dartoli.model.Match
import com.example.dartoli.model.Player

private lateinit var binding: ActivityStatisticsBinding

private lateinit var player1_selecting_button: TextView
private lateinit var player2_selecting_button: TextView
private lateinit var popupMenu_players: PopupMenu
private lateinit var player1: Player
private lateinit var player2: Player

private lateinit var games_list: ArrayList<Game>

private lateinit var gameStatisticsAdapter: GameStatisticsStatusAdapter
private lateinit var rvGameStatistic: RecyclerView

private var all_game_statistics = arrayListOf<GameStatisticItem>()

class StatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db = MatchesDatabaseHandler(this)
        val matches_list = db.readAllMatches()

        games_list = GamesDatabaseHandler(this).readAllGames()

        player1_selecting_button = binding.tvPlayer1
        player2_selecting_button = binding.tvPlayer2
        popupMenu_players = PopupMenu(this, player1_selecting_button)
        popupMenu_players = PopupMenu(this, player2_selecting_button)

        val playersDB = PlayerDatabaseHandler(this)
        val player_list = playersDB.readAllPlayers()
        player1_selecting_button.setText(player_list[0].playerName)
        player1 = player_list[0]
        player2_selecting_button.setText(player_list[1].playerName)
        player2 = player_list[1]
        refresh_statistics(player1, player2, matches_list)

        player1_selecting_button.setOnClickListener {
            popupMenu_players = PopupMenu(this, player1_selecting_button)
            popupMenu_players.menuInflater.inflate(R.menu.popup_menu, popupMenu_players.menu)
            popupMenu_players.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                player1_selecting_button.setText(menuItem.title)
                for(element in player_list){
                    if (element.playerName.equals(menuItem.title.toString())){
                        player1 = element
                        refresh_statistics(player1, player2, matches_list)
                        setupPlayerStatusRecyclerView()
                    }
                }
                true
            }
            if (popupMenu_players.getMenu().isNotEmpty()){
                popupMenu_players.getMenu().clear()
            }
            for (i in player_list.indices) {
                if (!player_list[i].equals(player1) && !player_list[i].equals(player2))
                    popupMenu_players.getMenu().add(player_list[i].playerName)
            }
            popupMenu_players.show()
        }

        player2_selecting_button.setOnClickListener {
            popupMenu_players = PopupMenu(this, player2_selecting_button)
            popupMenu_players.menuInflater.inflate(R.menu.popup_menu, popupMenu_players.menu)
            popupMenu_players.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                player2_selecting_button.setText(menuItem.title)
                for(element in player_list){
                    if (element.playerName.equals(menuItem.title.toString())){
                        player2 = element
                        refresh_statistics(player1, player2, matches_list)
                        setupPlayerStatusRecyclerView()
                    }
                }
                true
            }
            if (popupMenu_players.getMenu().isNotEmpty()){
                popupMenu_players.getMenu().clear()
            }
            for (i in player_list.indices) {
                if (!player_list[i].equals(player1) && !player_list[i].equals(player2))
                    popupMenu_players.getMenu().add(player_list[i].playerName)
            }
            popupMenu_players.show()
        }

        rvGameStatistic = binding.rvRecycler
        setupPlayerStatusRecyclerView()

    }

    fun refresh_statistics(player1: Player, player2: Player, matches: ArrayList<Match>){
        var player1_name = player1.playerName
        var player2_name = player2.playerName
        if(all_game_statistics.size > 0) all_game_statistics.clear()

        var player1_wins = 0
        var player1_sets = 0
        var player1_legs = 0
        var player1_highest_win: Int? = null
        var player1_longest_win_street = 0
        var player1_actual_street = 0
        var player2_wins = 0
        var player2_sets = 0
        var player2_legs = 0
        var player2_highest_win: Int? = null
        var player2_longest_win_street = 0
        var player2_actual_street = 0

        for (match in matches) {
            if (match.player_ids.size == 2) {
                if (match.player_ids[0] == player1.id && match.player_ids[1] == player2.id) {
                    player1_wins++
                    player1_legs = player1_legs + match.won_legs[0]
                    player1_sets = player1_sets + match.won_sets[0]
                    player2_legs = player2_legs + match.won_legs[1]
                    player2_sets = player2_sets + match.won_sets[1]
                    if (player1_actual_street < 1) {
                        player1_actual_street = 1
                    } else {
                        player1_actual_street++
                    }
                    player2_actual_street = 0 - player1_actual_street
                    if (player1_actual_street > player1_longest_win_street) player1_longest_win_street =
                        player1_actual_street
                }
                if (match.player_ids[1] == player1.id && match.player_ids[0] == player2.id) {
                    player2_wins++
                    player2_legs = player2_legs + match.won_legs[0]
                    player2_sets = player2_sets + match.won_sets[0]
                    player1_legs = player1_legs + match.won_legs[1]
                    player1_sets = player1_sets + match.won_sets[1]
                    if (player2_actual_street < 1) {
                        player2_actual_street = 1
                    } else {
                        player2_actual_street++
                    }
                    player1_actual_street = 0 - player2_actual_street
                    if (player2_actual_street > player2_longest_win_street) player2_longest_win_street =
                        player2_actual_street
                }
            }
        }
        all_game_statistics.add(
            GameStatisticItem(
                "Gesamt",
                player1_name,
                player2_name,
                player1_wins,
                player1_sets,
                player1_legs,
                player1_highest_win.toString(),
                player1_longest_win_street,
                player1_actual_street,
                player2_wins,
                player2_sets,
                player2_legs,
                player2_highest_win.toString(),
                player2_longest_win_street,
                player2_actual_street
            )
        )
        for (game in games_list) {
            var player1_wins = 0
            var player1_sets = 0
            var player1_legs = 0
            var player1_highest_win: Int? = null
            var player1_longest_win_street = 0
            var player1_actual_street = 0
            var player2_wins = 0
            var player2_sets = 0
            var player2_legs = 0
            var player2_highest_win: Int? = null
            var player2_longest_win_street = 0
            var player2_actual_street = 0

            for (match in matches) {
                if (match.game_id == game.id && match.player_ids.size == 2) {
                    if (match.player_ids[0] == player1.id && match.player_ids[1] == player2.id) {
                        player1_wins++
                        player1_legs = player1_legs + match.won_legs[0]
                        player1_sets = player1_sets + match.won_sets[0]
                        player2_legs = player2_legs + match.won_legs[1]
                        player2_sets = player2_sets + match.won_sets[1]
                        if (player1_actual_street < 1) {
                            player1_actual_street = 1
                        } else {
                            player1_actual_street++
                        }
                        player2_actual_street = 0 - player1_actual_street
                        if (player1_actual_street > player1_longest_win_street) player1_longest_win_street =
                            player1_actual_street
                    }
                    if (match.player_ids[1] == player1.id && match.player_ids[0] == player2.id) {
                        player2_wins++
                        player2_legs = player2_legs + match.won_legs[0]
                        player2_sets = player2_sets + match.won_sets[0]
                        player1_legs = player1_legs + match.won_legs[1]
                        player1_sets = player1_sets + match.won_sets[1]
                        if (player2_actual_street < 1) {
                            player2_actual_street = 1
                        } else {
                            player2_actual_street++
                        }
                        player1_actual_street = 0 - player2_actual_street
                        if (player2_actual_street > player2_longest_win_street) player2_longest_win_street =
                            player2_actual_street
                    }
                }
            }
            all_game_statistics.add(
                GameStatisticItem(
                    game.name,
                    player1_name,
                    player2_name,
                    player1_wins,
                    player1_sets,
                    player1_legs,
                    player1_highest_win.toString(),
                    player1_longest_win_street,
                    player1_actual_street,
                    player2_wins,
                    player2_sets,
                    player2_legs,
                    player2_highest_win.toString(),
                    player2_longest_win_street,
                    player2_actual_street
                )
            )
        }
    }
    private fun setupPlayerStatusRecyclerView() {
        rvGameStatistic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gameStatisticsAdapter = GameStatisticsStatusAdapter(all_game_statistics, ContextCompat.getColor(this, R.color.cricket_background_color))
        rvGameStatistic.adapter = gameStatisticsAdapter
    }

    override fun onBackPressed() {
        startActivity(Intent(this@StatisticsActivity, MainActivity::class.java))
    }
}