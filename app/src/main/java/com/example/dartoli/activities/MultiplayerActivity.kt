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
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import com.example.dartoli.R
import com.example.dartoli.data.Datasource
import com.example.dartoli.data.MyDatabaseHandler
import com.example.dartoli.databinding.ActivityMultiplayerBinding
import com.example.dartoli.model.Player


class MultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplayerBinding
    private lateinit var game_selection_button: TextView
    private lateinit var player_adding_button: ImageView
    private lateinit var popupMenu_games: PopupMenu
    private lateinit var popupMenu_players: PopupMenu
    private var selected_players = arrayListOf<Player>()
    private var players_dataset = arrayListOf<Player>()
    private lateinit var start_game_button: TextView
    val games_dataset = Datasource().loadGames()
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
                for(element in games_dataset){
                    if (element.titel.equals(menuItem.title)) {
                        element.selected = true
                    } else {
                        element.selected = false
                    }
                }
                true
            }
            if (popupMenu_games.getMenu().isNotEmpty()){
                popupMenu_games.getMenu().clear()
            }
            for (i in games_dataset.indices) {
                if (!games_dataset[i].selected)
                    popupMenu_games.getMenu().add(games_dataset[i].titel)
            }
            popupMenu_games.show()
        }

        player_adding_button.setOnClickListener {
            val myDB = MyDatabaseHandler(this)
            players_dataset = myDB.readAllPlayers()

            if (popupMenu_players.getMenu().isNotEmpty()){
                popupMenu_players.getMenu().clear()
            }
            for (element in players_dataset){
                if(!selected_players.contains(element)){
                    popupMenu_players.getMenu().add(element.playerName)
                }
            }
            if (popupMenu_players.getMenu().isNotEmpty()){
                popupMenu_players.show()
            }

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
                for (i in players_dataset.indices) {
                    if (players_dataset[i].playerName.equals(menuItem.title)){
                        selected_players.add(players_dataset[i])
                    }
                }
                true
            }
        }

        start_game_button = binding.startGameButton
        start_game_button.setOnClickListener{
            Toast.makeText(this, selected_players.size.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
