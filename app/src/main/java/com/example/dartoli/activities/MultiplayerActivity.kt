package com.example.dartoli.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.PopupMenu
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import com.example.dartoli.Counting.CountingGameActivity
import com.example.dartoli.R
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityMultiplayerBinding
import com.example.dartoli.model.Game
import com.example.dartoli.model.Player
import com.example.dartoli.Cricket.CricketActivity

class MultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplayerBinding
    private lateinit var game_selection_button: TextView
    private lateinit var player_adding_button: ImageView
    private lateinit var popupMenu_games: PopupMenu
    private lateinit var popupMenu_players: PopupMenu
    private var selected_players = arrayListOf<Player>()
    private var players_dataset = arrayListOf<Player>()
    private lateinit var start_game_button: TextView
    private lateinit var player_creation_button: ImageView
    private lateinit var player_list_view: LinearLayout
    private lateinit var player_list_scroll_view: ScrollView
    lateinit var customDialog: Dialog
    private lateinit var chosen_game : Game

    val LEGS_KEY = "legs"
    val SETS_KEY = "sets"
    val PLAYERS_KEY = "players"
    val POINTS_KEY = "points"


    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        game_selection_button = binding.gameSelectionButton
        popupMenu_games = PopupMenu(this, game_selection_button)

        val gamesDB = GamesDatabaseHandler(this)
        val games_list = gamesDB.readAllGames()
        game_selection_button.setText(games_list[0].name)
        chosen_game = games_list[0]


        game_selection_button.setOnClickListener {
            popupMenu_games.menuInflater.inflate(R.menu.popup_menu, popupMenu_games.menu)
            popupMenu_games.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                game_selection_button.setText(menuItem.title)
                for(element in games_list){
                    if (element.name.equals(menuItem.title.toString())){
                        chosen_game = element
                    }
                }
                true
            }
            if (popupMenu_games.getMenu().isNotEmpty()){
                popupMenu_games.getMenu().clear()
            }
            for (i in games_list.indices) {
                if (!games_list[i].equals(chosen_game))
                    popupMenu_games.getMenu().add(games_list[i].name)
            }
            popupMenu_games.show()
        }



        player_adding_button = binding.btnAddExistingPlayer
        popupMenu_players = PopupMenu(this, player_adding_button)
        player_list_view = binding.playerNamesLayout
        player_list_scroll_view = binding.w
        player_adding_button.setOnClickListener {
            val myDB = PlayerDatabaseHandler(this)
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
                add_player_to_list(menuItem.title.toString())
            }
        }


        player_creation_button = binding.btnAddNewPlayer
        player_creation_button.setOnClickListener{
            customDialog.show()
        }


        customDialog = Dialog(this);
        customDialog.setContentView(R.layout.custom_dialog_resource);
        customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        val btnSubmit = customDialog.findViewById<ImageView>(R.id.btnSubmit)
        val btnDismiss = customDialog.findViewById<ImageView>(R.id.btnDismiss)
        val newName = customDialog.findViewById<EditText>(R.id.edit_text_player_name)
        btnSubmit.setOnClickListener(View.OnClickListener() {
            val myDB = PlayerDatabaseHandler(this)
            myDB.addPlayer(newName.text.toString())
            players_dataset = myDB.readAllPlayers()
            add_player_to_list(newName.text.toString())
            customDialog.dismiss();
        })
        btnDismiss.setOnClickListener(View.OnClickListener() {
            customDialog.dismiss();
        })


        start_game_button = binding.startGameButton
        start_game_button.setOnClickListener{
            if (ckeck_game_conditions()){
                val winning_legs = Integer.parseInt(binding.etWinningLegs.text.toString())
                val winning_sets = Integer.parseInt(binding.etWinningSets.text.toString())
                var id_array = IntArray(selected_players.size)
                for (i in 0..selected_players.size -1){
                    id_array[i] = selected_players[i].id
                }
                if (game_selection_button.text.equals("Cricket")){
                    val intent = Intent(this@MultiplayerActivity, CricketActivity::class.java)
                    intent.putExtra(LEGS_KEY, winning_legs)
                    intent.putExtra(SETS_KEY, winning_sets)
                    intent.putExtra(PLAYERS_KEY, id_array)
                    startActivity(intent)
                    finish()
                } else if (game_selection_button.text.equals("501")){
                    val intent = Intent(this@MultiplayerActivity, CountingGameActivity::class.java)
                    intent.putExtra(LEGS_KEY, winning_legs)
                    intent.putExtra(SETS_KEY, winning_sets)
                    intent.putExtra(PLAYERS_KEY, id_array)
                    intent.putExtra(POINTS_KEY, 501)
                    startActivity(intent)
                    finish()
                } else if (game_selection_button.text.equals("301")) {
                    val intent = Intent(this@MultiplayerActivity, CountingGameActivity::class.java)
                    intent.putExtra(LEGS_KEY, winning_legs)
                    intent.putExtra(SETS_KEY, winning_sets)
                    intent.putExtra(PLAYERS_KEY, id_array)
                    intent.putExtra(POINTS_KEY, 301)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Konditionen nicht erfüllt", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this@MultiplayerActivity, MainActivity::class.java))
    }

    private fun add_player_to_list(name: String): Boolean{
        val tv = TextView(this)
        val layoutParams = LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)
        layoutParams.gravity = Gravity.CENTER
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
        tv.gravity = Gravity.CENTER
        tv.setText(name)
        tv.id = name.hashCode()
        tv.setTextColor(getResources().getColor(R.color.black))
        tv.setLayoutParams(layoutParams)
        tv.setOnClickListener() {
            for (element in players_dataset){
                if (element.playerName == name){
                    selected_players.remove(element)
                    player_list_view.removeView(tv)
                    if(selected_players.size == 0){
                        player_list_scroll_view.setVisibility(View.GONE)
                    }
                }
            }
        }
        player_list_view.addView(tv)
        player_list_scroll_view.setVisibility(View.VISIBLE)
        for (i in players_dataset.indices) {
            if (players_dataset[i].playerName.equals(name)){
                selected_players.add(players_dataset[i])
            }
        }
        return true
    }

    private fun ckeck_game_conditions(): Boolean {
        if (binding.etWinningLegs.text.isNotEmpty() && binding.etWinningSets.text.isNotEmpty() && selected_players.size > 1){
            return true
        } else {
            return false
        }
    }
}
