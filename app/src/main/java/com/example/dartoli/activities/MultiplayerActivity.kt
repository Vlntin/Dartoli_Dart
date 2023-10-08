package com.example.dartoli.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dartoli.R
import com.example.dartoli.data.Datasource
import com.example.dartoli.databinding.ActivityMultiplayerBinding


class MultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplayerBinding
    private lateinit var game_selection_button: TextView
    private lateinit var player_adding_button: ImageView
    private lateinit var popupMenu_games: PopupMenu
    private lateinit var popupMenu_players: PopupMenu
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize data.
        val games_dataset = Datasource().loadGames()
        val players_dataset = Datasource().loadPlayers()
        game_selection_button = binding.gameSelectionButton
        game_selection_button.setText(games_dataset[0].titel)
        popupMenu_games = PopupMenu(this, game_selection_button)
        player_adding_button = binding.btnAddExistingPlayer
        popupMenu_players = PopupMenu(this, player_adding_button)
        val player_list_view = binding.playerNamesLayout
        val player_list_scroll_view = binding.w

        game_selection_button.setOnClickListener {
            popupMenu_games.menuInflater.inflate(R.menu.popup_menu, popupMenu_games.menu)
            popupMenu_games.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                game_selection_button.setText(menuItem.title)
                true
            }
            popupMenu_games.show()
        }

        for (i in games_dataset.indices) {
            popupMenu_games.getMenu().add(games_dataset[i].titel)
        }


        player_adding_button.setOnClickListener {
            popupMenu_players.menuInflater.inflate(R.menu.popup_menu, popupMenu_players.menu)
            popupMenu_players.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                val tv = TextView(this)
                val layoutParams = LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)
                layoutParams.gravity = Gravity.CENTER
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
                tv.gravity = Gravity.CENTER
                tv.setText(menuItem.title)
                tv.setTextColor(getResources().getColor(R.color.black))
                tv.setLayoutParams(layoutParams)
                player_list_view.addView(tv)
                player_list_scroll_view.setVisibility(View.VISIBLE)
                true
            }
            popupMenu_players.show()
        }

        for (i in players_dataset.indices) {
            popupMenu_players.getMenu().add(players_dataset[i].playerName)
        }
    }
}
