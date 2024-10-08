package com.example.dartoli.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
import com.example.dartoli.Counting.CountingGame
import com.example.dartoli.Counting.CountingGameActivity
import com.example.dartoli.Counting.CountingPlayer
import com.example.dartoli.Cricket.CricketActivity
import com.example.dartoli.Cricket.CricketGame
import com.example.dartoli.Cricket.CricketPlayer
import com.example.dartoli.R
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.MatchesDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityMultiplayerBinding
import com.example.dartoli.model.Game
import com.example.dartoli.model.Player

class MultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplayerBinding

    private lateinit var game_selection_button: TextView
    private lateinit var player_adding_button: ImageView
    private lateinit var player_creation_button: ImageView
    private lateinit var start_game_button: TextView

    private var selected_players = arrayListOf<Player>()
    private var players_dataset = arrayListOf<Player>()
    private lateinit var chosen_game : Game

    private lateinit var popupMenu_games: PopupMenu
    private lateinit var popupMenu_players: PopupMenu
    private lateinit var player_list_view: LinearLayout
    private lateinit var player_list_scroll_view: ScrollView
    lateinit var customDialog: Dialog

    val GAME_KEY = "game"

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        game_selection_button = binding.gameSelectionButton
        popupMenu_games = PopupMenu(this, game_selection_button)

        val games_list = GamesDatabaseHandler(this).readAllGames()

        val matches = MatchesDatabaseHandler(this).readAllMatches()
        if (!matches.isEmpty()) {
            val last_played_game_id = matches.last().game_id
            for (game in games_list) {
                if (game.id == last_played_game_id) {
                    game_selection_button.setText(game.name)
                    chosen_game = game
                }
            }
        } else{
            game_selection_button.setText(games_list[0].name)
            chosen_game = games_list[0]
        }

        game_selection_button.setOnClickListener {
            popupMenu_games.menuInflater.inflate(com.example.dartoli.R.menu.popup_menu, popupMenu_games.menu)
            popupMenu_games.setOnMenuItemClickListener { menuItem ->
                game_selection_button.setText(menuItem.title)
                for(element in games_list){
                    if (element.name.equals(menuItem.title.toString())) chosen_game = element
                }
                true
            }
            if (popupMenu_games.getMenu().isNotEmpty()) popupMenu_games.getMenu().clear()
            for (i in games_list.indices) {
                if (!games_list[i].equals(chosen_game)) popupMenu_games.getMenu().add(games_list[i].name)
            }
            popupMenu_games.show()
        }

        player_adding_button = binding.btnAddExistingPlayer
        popupMenu_players = PopupMenu(this, player_adding_button)
        player_list_view = binding.playerNamesLayout
        player_list_scroll_view = binding.w
        player_adding_button.setOnClickListener {
            players_dataset = PlayerDatabaseHandler(this).readAllPlayers()

            if (popupMenu_players.getMenu().isNotEmpty()) popupMenu_players.getMenu().clear()
            for (element in players_dataset){
                if(!selected_players.contains(element)) popupMenu_players.getMenu().add(element.playerName)
            }
            if (popupMenu_players.getMenu().isNotEmpty()) popupMenu_players.show()
            popupMenu_players.menuInflater.inflate(com.example.dartoli.R.menu.popup_menu, popupMenu_players.menu)
            popupMenu_players.setOnMenuItemClickListener { menuItem ->
                add_player_to_list(menuItem.title.toString())
            }
        }
        player_creation_button = binding.btnAddNewPlayer
        player_creation_button.setOnClickListener{
            customDialog.show()
        }

        customDialog = Dialog(this)
        customDialog.setContentView(com.example.dartoli.R.layout.custom_dialog_resource)
        customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        val btnSubmit = customDialog.findViewById<ImageView>(com.example.dartoli.R.id.btnSubmit)
        val btnDismiss = customDialog.findViewById<ImageView>(com.example.dartoli.R.id.btnDismiss)
        val newName = customDialog.findViewById<EditText>(com.example.dartoli.R.id.edit_text_player_name)
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
                if (game_selection_button.text.equals("Cricket")){
                    val intent = Intent(this@MultiplayerActivity, CricketActivity::class.java)
                    var cricket_players = arrayListOf<CricketPlayer>()
                    for (player in selected_players){
                        cricket_players.add(CricketPlayer(player.playerName, 0, 0,0,0,0,0,0,0, false, false, false, false, false, false, false, 0, winning_legs, 0, winning_sets, 0, 0))
                    }
                    var game = CricketGame(winning_legs, winning_sets, cricket_players)
                    intent.putExtra(GAME_KEY, game)
                    startActivity(intent)
                    finish()
                } else if (game_selection_button.text.equals("501")){
                    val intent = Intent(this@MultiplayerActivity, CountingGameActivity::class.java)
                    var counting_players = arrayListOf<CountingPlayer>()
                    for (player in selected_players){
                        counting_players.add(CountingPlayer(player.id, player.playerName, 501, 0,0.0,0,winning_legs,0, winning_sets,0, 0, arrayListOf<Int>(), 0, 0, 0.0, arrayListOf<Double>(), arrayListOf<Int>(), 0, arrayListOf<Int>(), arrayListOf<Int>(), arrayListOf<Int>(), arrayListOf<Int>()))
                    }
                    var game = CountingGame(winning_legs, winning_sets, counting_players, 501)
                    intent.putExtra(GAME_KEY, game)
                    startActivity(intent)
                    finish()
                } else if (game_selection_button.text.equals("301")) {
                    val intent = Intent(this@MultiplayerActivity, CountingGameActivity::class.java)
                    var counting_players = arrayListOf<CountingPlayer>()
                    for (player in selected_players){
                        counting_players.add(CountingPlayer(player.id, player.playerName, 301, 0,0.0,0,winning_legs,0, winning_sets,0, 0, arrayListOf<Int>(), 0, 0, 0.0, arrayListOf<Double>(), arrayListOf<Int>(), 0, arrayListOf<Int>(), arrayListOf<Int>(), arrayListOf<Int>(), arrayListOf<Int>()))
                    }
                    var game = CountingGame(winning_legs, winning_sets, counting_players, 301)
                    intent.putExtra(GAME_KEY, game)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Ungültige Einstellungen", Toast.LENGTH_LONG).show()
            }
        }
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
                    if(selected_players.size == 0) player_list_scroll_view.setVisibility(View.GONE)
                }
            }
        }
        player_list_view.addView(tv)
        player_list_scroll_view.setVisibility(View.VISIBLE)
        for (i in players_dataset.indices) {
            if (players_dataset[i].playerName.equals(name)) selected_players.add(players_dataset[i])
        }
        return true
    }

    private fun ckeck_game_conditions(): Boolean {
        return (binding.etWinningLegs.text.isNotEmpty() && binding.etWinningSets.text.isNotEmpty() && selected_players.size > 1)
    }

    override fun onBackPressed() {
        startActivity(Intent(this@MultiplayerActivity, MainActivity::class.java))
    }
}
